this.statistics = this.statistics||{};

(function(){

    /**
     * 一些静态方法和属性
     */
    var utils = {
        //修剪两端空白字符和换行符
        trim:function (s){
            return s.replace(/(^\s*)|(\s*$)|(\n)/g, "");
        },
        //是否开启cookie
        cookieEnabled:navigator.cookieEnabled,
        //是否支持javas
        javaEnabled:navigator.javaEnabled(),
        //flash版本
        flashVersion:function(){
            var a = "";
            if (navigator.plugins && navigator.mimeTypes.length) {
                var b = navigator.plugins["Shockwave Flash"];
                b && b.description && (a = b.description.replace(/^.*\s+(\S+)\s+\S+$/, "$1"))
            } else if (window.ActiveXObject) try {
                if (b = new ActiveXObject("ShockwaveFlash.ShockwaveFlash"))(a = b.GetVariable("$version")) && (a = a.replace(/^.*\s+(\d+),(\d+).*$/, "$1.$2"))
            } catch(f) {}
            return a
        }(),
        //颜色深度
        colorDepth:(window.screen.colorDepth || 0) + "-bit",
        //屏幕尺寸
        screenSize:(window.screen.width || 0) + "x" + (window.screen.height || 0),
        //语言
        language:navigator.language || navigator.browserLanguage || navigator.systemLanguage || navigator.userLanguage || "",
        //object数据解析
        object:{
            //obj转字符串
            stringify:function() {
                function a(a) { / ["\\\x00-\x1f]/.test(a)&&(a=a.replace(/["\\\x00 - \x1f]/g,
                    function(a) {
                        var b = f[a];
                        if (b) return b;
                        b = a.charCodeAt();
                        return "\\u00" + Math.floor(b / 16).toString(16) + (b % 16).toString(16)
                    }));
                    return '"' + a + '"'
                }
                function b(a) {
                    return 10 > a ? "0" + a: a
                }
                var f = {
                    "\b": "\\b",
                    "\t": "\\t",
                    "\n": "\\n",
                    "\f": "\\f",
                    "\r": "\\r",
                    '"': '\\"',
                    "\\": "\\\\"
                };
                return function(d) {
                    switch (typeof d) {
                        case "undefined":
                            return "undefined";
                        case "number":
                            return isFinite(d) ? String(d) : "null";
                        case "string":
                            return a(d);
                        case "boolean":
                            return String(d);
                        default:
                            if (d === null) return "null";
                            if (d instanceof Array) {
                                var e = ["["],
                                    f = d.length,
                                    n,
                                    g,
                                    k;
                                for (g = 0; g < f; g++) switch (k = d[g], typeof k) {
                                    case "undefined":
                                    case "function":
                                    case "unknown":
                                        break;
                                    default:
                                        n && e.push(","),
                                            e.push(this.stringify(k)),
                                            n = 1
                                }
                                e.push("]");
                                return e.join("")
                            }
                            if (d instanceof Date) return '"' + d.getFullYear() + "-" + b(d.getMonth() + 1) + "-" + b(d.getDate()) + "T" + b(d.getHours()) + ":" + b(d.getMinutes()) + ":" + b(d.getSeconds()) + '"';
                            n = ["{"];
                            g = this.stringify;
                            for (f in d) if (Object.prototype.hasOwnProperty.call(d, f)) switch (k = d[f], typeof k) {
                                case "undefined":
                                case "unknown":
                                case "function":
                                    break;
                                default:
                                    e && n.push(","),
                                        e = 1,
                                        n.push(g(f) + ":" + g(k))
                            }
                            n.push("}");
                            return n.join("")
                    }
                }
            } ()
        },
        cookie:{
            //单位(默认分、天d、时h)
            set:function(name,value,expiry) {

                //分转成毫秒，以分为单位
                var minute = expiry.match(/\d+/)[0] * 60 * 1e3;
                //单位
                var unit = expiry.charAt(expiry.length-1);

                switch (unit){
                    case 'd':
                        //天
                        minute = minute * 60 * 24;
                        break;
                    case 'h':
                        //时
                        minute *= 60;
                        break;
                }

                //当前时间和过期时间相加
                var date = new Date();
                date.setTime(date.getTime()+minute);
                //给cookie赋值
                document.cookie = name+"="+value+";expires="+date.toGMTString();
            },
            get:function(name) {
                return (name = RegExp("(^| )" + name + "=([^;]*)(;|$)").exec(document.cookie)) ? name[2] : null;
            },
            remove:function(name){
                utils.cookie.set(name,"","-1");
            }
        }
    };


    /**
     * 事件
     */
    var event = {
        addEventListener:function(target,type,func){
            if(target.addEventListener)target.addEventListener(type,func,false);
            else {
                if(type === "DOMContentLoaded")
                {
                    //ie,6、7、8
                    function isReady()
                    {
                        try{
                            document.documentElement.doScroll("left");
                            func();
                        }
                        catch(e)
                        {
                            setTimeout(isReady,1);
                        }
                    }
                    isReady()
                }
                else target.attachEvent("on"+type,function(e){
                    func.call(target,e);
                });

            }
        },
        preventDefault:function(target) {
            target.preventDefault ? target.preventDefault() : target.returnValue = false;
        }
    };


    /**
     * url请求
     */
    var urlRequest = {
        load:function(parms){

            var url,method,async,data,timeout,cache,symbol,random,xhr;
            method = (parms.method||"GET").toUpperCase();
            async = "async" in parms?parms.async:true;
            data = parms.data;
            url = parms.url+(method === "GET"?"?"+this.formatData(data):'');
            timeout = parms.timeout;
            cache = parms.cache;
            symbol = url.indexOf("?")!=-1?"&":"?";
            //不需要缓存和post请求方式把随机数去掉，因为post没有缓存
            random = (!cache&&method!=="POST")?symbol+"t=" + Math.random():'';
            xhr = new XMLHttpRequest();

            xhr.open(method, url+random, async);

            //请求如果是post需要设置一下头文件
            if ( method === "POST")xhr.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');

            parms.onBeforeSend && parms.onBeforeSend(xhr);

            //请求事件状态变化监听
            xhr.onreadystatechange = function () {
                if ( xhr.readyState != 4 ) return;

                // 出错
                if ( xhr.status != 200 && xhr.status != 304 ) {
                    parms.onError && parms.onError("未知");
                    return;
                }
                var suffix = url.split('.');
                //完成
                parms.onSuccess && parms.onSuccess(suffix[suffix.length-1]==='json'?JSON.parse(xhr.responseText):xhr.responseText);
            };

            xhr.send(this.formatData(data));

            //多少秒以后终止请求
            if ( timeout ) {
                setTimeout(function () {
                    xhr.onreadystatechange = function () {};
                    xhr.abort();
                    parms.onError && parms.onError("加载时间过长请求被终止");
                }, timeout);
            }
        },

        /*json转化成post参数*/
        formatData:function(json){
            var result = null;
            for(var key in json){
                if(!result)result = (key +"="+ json[key]);
                else result += ("&"+key +"="+ json[key]);
            }
            return result;
        }
    };

    var uid = function (len, radix) {
        var CHARS = '0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz'.split('');
        var chars = CHARS, uuid = [], i;
        radix = radix || chars.length;

        if (len) {
            // Compact form
            for (i = 0; i < len; i++) uuid[i] = chars[0 | Math.random()*radix];
        } else {
            // rfc4122, version 4 form
            var r;

            // rfc4122 requires these characters
            uuid[8] = uuid[13] = uuid[18] = uuid[23] = '-';
            uuid[14] = '4';

            // Fill in random data.  At i==19 set the high bits of clock sequence as
            // per rfc4122, sec. 4.1.5
            for (i = 0; i < 36; i++) {
                if (!uuid[i]) {
                    r = 0 | Math.random()*16;
                    uuid[i] = chars[(i == 19) ? (r & 0x3) | 0x8 : r];
                }
            }
        }

        return uuid.join('');
    };


    /**
     * 要收集的信息
     */
    var domTime;


    /**
     * 获取uid
     */
    function getUid(){
        if(!utils.cookie.get('uid'))utils.cookie.set("uid",uid(),"30");
        return utils.cookie.get('uid');
    }

    /**
     * 页面dom加载完毕触发，不包含多媒体和图形
     */
    event.addEventListener(window,"DOMContentLoaded",function(e){
        statistics.recordDom();
    });

    /**
     * 页面和图像加载完毕触发，不包含多媒体
     */
    /*event.addEventListener(window,"load",function(e){
     var loadTime = +new Date();

     if (window.performance && performance.timing){
     //计算出各项状态的花费时间
     function logTime(a) {
     var timing = performance.timing;
     var s = timing[a + "Start"] ? timing[a + "Start"] : 0;
     var e = timing[a + "End"] ? timing[a + "End"] : 0;
     return {
     start: s,
     end: e,
     value: 0 < e - s ? e - s: 0
     }
     }

     //卸载网页的时间戳
     var unloadTime = logTime("navigation");
     //发送http请求或开始读取本地缓存时的时间戳
     var requestTime = logTime("request");
     //分析好的一组时间
     var times = {
     //卸载到加载下一个网页之间的时间戳
     netAll: requestTime.start - unloadTime.start,
     //查询域名所消耗的时间戳
     netDns: logTime("domainLookup").value,
     //http请求与服务器连接消耗时间戳
     netTcp: logTime("connect").value,
     //浏览器开始请求从服务器收到第一个字节消耗的时间戳
     srv: logTime("response").start - requestTime.start,
     //dom元素读取消耗的时间戳
     dom: performance.timing.domInteractive - performance.timing.fetchStart,
     //网页加载完成所消耗的时间戳
     load: loadTime - unloadTime.start
     };
     }
     })*/

    /**
     * 页面卸载前、关闭浏览器多会触发的事件
     */
    event.addEventListener(window,"beforeunload",function(e){
        statistics.recordLeave();
    })

    /**
     * 浏览器卸载页面后执行的事件
     */
    /*event.addEventListener(window,"unload",function(e){

     })*/

    /**
     * 监听全局点击事件
     */
    event.addEventListener(document,"click",function(e){
        handlerUserEvent(e);
    })

    /**
     * 监听全局鼠标按下事件
     */
    event.addEventListener(document,"mousedown",function(e){
        handlerUserEvent(e);
    })

    /**
     * 监听全局鼠标弹起事件
     */
    event.addEventListener(document,"mouseup",function(e){
        handlerUserEvent(e);
    })

    /**
     * 处理用户发出的事件
     */
    function handlerUserEvent(e){
        var currentTarget = e.target||e.srcElement;
        var tagName = currentTarget.tagName.toLowerCase();

        //collectInfo.event.type = e.type;
    }

    /**
     * 获取上一页面的状态信息
     */
    function getLog(str,key){
        var reg = new RegExp(key+"=([^&]*)")
        var result = str.match(reg);
        return (result!=null&&result.length>1)?result[1]:null;
    }

    /**
     * 记录dom初始完成的一些状态
     */
    statistics.recordDom = function(){
        domTime = +new Date();

        var windowUrl=utils.trim(window.name);
        if(windowUrl && windowUrl.indexOf("|%")!==-1){
            var urlArray=windowUrl.split("|%"),orginName=urlArray[0];
            windowUrl=urlArray[1];
            //把window.name归还给应用程序
            window.name=orginName;
            //发送打点请求
            urlRequest.load({
                url:'http://test.tongji.yinchengmall.com/visit/send.do',
                data:{
                    tj_uid:getUid(),
                    tj_et:'3',
                    tj_tt:getLog(windowUrl,"tj_tt"),
                    tj_url:getLog(windowUrl,"tj_url"),
                    referrer:getLog(windowUrl,"referrer"),
                    tj_tel:getLog(windowUrl,"tj_tel"),
                    tj_tll:getLog(windowUrl,"tj_tll"),
                    tj_sr:utils.screenSize,
                    tj_ja:utils.javaEnabled?1:0,
                    tj_fa:utils.flashVersion?1:0,
                    tj_fv:utils.flashVersion,
                    tj_cb:utils.colorDepth,
                    tj_cl:utils.language
                }
            });
        }

    }

    /**
     * 记录离开页面的一些状态
     */
    statistics.recordLeave = function(){
        window.name=window.name+'|%'+urlRequest.formatData({
                tj_tel:domTime,
                tj_tll:+new Date(),
                tj_tt:document.title,
                tj_url:location.href,
                referrer:document.referrer
            });

    }


}());