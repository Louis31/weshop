/*!
 * Check browser
 * Author: zhugao
 * Date: 2013-7-23
 */

var domainBase = '';
 
var checkBrowser = {
    ua: navigator.userAgent,
    help: function () {
        location.href = domainBase + '/help/chrome';
    },
    goIndex: function () {
        location.href = domainBase + '/dashboard/index';
    },
    subtitleEnabled: function () {
        return "track" in document.createElement("track");
    },
    scopedEnabled: function () {
        return "scoped" in document.createElement("style");
    },
    isChrome: function () {
        if (this.ua.indexOf("Chrome") !== -1) return true;
        return false;
    },
    isIOS: function () {
        if (/(iPhone|iPad|iPod)/i.test(this.ua)) return true;
        return false;
    },
    isAndroid: function () {
        if (/Android/i.test(this.ua)) return true;
        return false;
    },
    isMac: function() {
        return navigator.platform.toUpperCase().indexOf('MAC') >= 0;
    },
    isSafari: function () {
        if (this.ua.indexOf("Safari") != -1) return true;
        return false;
    },
    isSougou: function () {
        if (navigator.userAgent.toLowerCase().indexOf('se 2.x') != -1) return true;
        return false;
    },
    is360: function () {
        if (this.subtitleEnabled() && this.scopedEnabled()) return true;
        return false;
    },

    // 检查所有页面
    checkAllPage: function () {

        if (this.isIOS() || this.isAndroid()) {
            return;
        }

        if (!this.isChrome()) {
            if ((this.isMac() || this.isIOS()) && this.isSafari()) {
                // 在IOS上使用safari
            } else {
                this.help();
            }
        } else {
            if (this.is360() || this.isSougou()) {
                // 360
                this.help();
            }
        }
    },

    // 检查chrome下载页面
    checkHelpPage: function () {
        if (this.isChrome()) {
            if (this.is360() || this.isSougou()) {
            } else {
                this.goIndex();
            }
        }
    }
};

var GLOBAL_CONFIG = GLOBAL_CONFIG,
    _global_config = GLOBAL_CONFIG || '';

if (_global_config) {
    // v1
    domainBase = window.BASE_URL.ROOT;
    if (!GLOBAL_CONFIG.isDownloadChromePage) {
        checkBrowser.checkAllPage();
    } else {
        checkBrowser.checkHelpPage();
    }
} else {
    // v2
    domainBase = window._global.url.base;
    checkBrowser.checkAllPage();
}
