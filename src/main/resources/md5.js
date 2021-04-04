function r(r, n) {
    var t = (65535 & r) + (65535 & n);
    return (r >> 16) + (n >> 16) + (t >> 16) << 16 | 65535 & t;
}

function n(r, n) {
    return r << n | r >>> 32 - n;
}

function t(t, o, e, f, u, a) {
    return r(n(r(r(o, t), r(f, a)), u), e);
}

function o(r, n, o, e, f, u, a) {
    return t(n & o | ~n & e, r, n, f, u, a);
}

function e(r, n, o, e, f, u, a) {
    return t(n & e | o & ~e, r, n, f, u, a);
}

function f(r, n, o, e, f, u, a) {
    return t(n ^ o ^ e, r, n, f, u, a);
}

function u(r, n, o, e, f, u, a) {
    return t(o ^ (n | ~e), r, n, f, u, a);
}

function a(n) {
    for (var t = 1732584193, a = -271733879, i = -1732584194, C = 271733878, h = 0; h < n.length; h += 16) {
        var c = t, g = a, d = i, m = C;
        a = u(a = u(a = u(a = u(a = f(a = f(a = f(a = f(a = e(a = e(a = e(a = e(a = o(a = o(a = o(a = o(a, i = o(i, C = o(C, t = o(t, a, i, C, n[h + 0], 7, -680876936), a, i, n[h + 1], 12, -389564586), t, a, n[h + 2], 17, 606105819), C, t, n[h + 3], 22, -1044525330), i = o(i, C = o(C, t = o(t, a, i, C, n[h + 4], 7, -176418897), a, i, n[h + 5], 12, 1200080426), t, a, n[h + 6], 17, -1473231341), C, t, n[h + 7], 22, -45705983), i = o(i, C = o(C, t = o(t, a, i, C, n[h + 8], 7, 1770035416), a, i, n[h + 9], 12, -1958414417), t, a, n[h + 10], 17, -42063), C, t, n[h + 11], 22, -1990404162), i = o(i, C = o(C, t = o(t, a, i, C, n[h + 12], 7, 1804603682), a, i, n[h + 13], 12, -40341101), t, a, n[h + 14], 17, -1502002290), C, t, n[h + 15], 22, 1236535329), i = e(i, C = e(C, t = e(t, a, i, C, n[h + 1], 5, -165796510), a, i, n[h + 6], 9, -1069501632), t, a, n[h + 11], 14, 643717713), C, t, n[h + 0], 20, -373897302), i = e(i, C = e(C, t = e(t, a, i, C, n[h + 5], 5, -701558691), a, i, n[h + 10], 9, 38016083), t, a, n[h + 15], 14, -660478335), C, t, n[h + 4], 20, -405537848), i = e(i, C = e(C, t = e(t, a, i, C, n[h + 9], 5, 568446438), a, i, n[h + 14], 9, -1019803690), t, a, n[h + 3], 14, -187363961), C, t, n[h + 8], 20, 1163531501), i = e(i, C = e(C, t = e(t, a, i, C, n[h + 13], 5, -1444681467), a, i, n[h + 2], 9, -51403784), t, a, n[h + 7], 14, 1735328473), C, t, n[h + 12], 20, -1926607734), i = f(i, C = f(C, t = f(t, a, i, C, n[h + 5], 4, -378558), a, i, n[h + 8], 11, -2022574463), t, a, n[h + 11], 16, 1839030562), C, t, n[h + 14], 23, -35309556), i = f(i, C = f(C, t = f(t, a, i, C, n[h + 1], 4, -1530992060), a, i, n[h + 4], 11, 1272893353), t, a, n[h + 7], 16, -155497632), C, t, n[h + 10], 23, -1094730640), i = f(i, C = f(C, t = f(t, a, i, C, n[h + 13], 4, 681279174), a, i, n[h + 0], 11, -358537222), t, a, n[h + 3], 16, -722521979), C, t, n[h + 6], 23, 76029189), i = f(i, C = f(C, t = f(t, a, i, C, n[h + 9], 4, -640364487), a, i, n[h + 12], 11, -421815835), t, a, n[h + 15], 16, 530742520), C, t, n[h + 2], 23, -995338651), i = u(i, C = u(C, t = u(t, a, i, C, n[h + 0], 6, -198630844), a, i, n[h + 7], 10, 1126891415), t, a, n[h + 14], 15, -1416354905), C, t, n[h + 5], 21, -57434055), i = u(i, C = u(C, t = u(t, a, i, C, n[h + 12], 6, 1700485571), a, i, n[h + 3], 10, -1894986606), t, a, n[h + 10], 15, -1051523), C, t, n[h + 1], 21, -2054922799), i = u(i, C = u(C, t = u(t, a, i, C, n[h + 8], 6, 1873313359), a, i, n[h + 15], 10, -30611744), t, a, n[h + 6], 15, -1560198380), C, t, n[h + 13], 21, 1309151649), i = u(i, C = u(C, t = u(t, a, i, C, n[h + 4], 6, -145523070), a, i, n[h + 11], 10, -1120210379), t, a, n[h + 2], 15, 718787259), C, t, n[h + 9], 21, -343485551),
            t = r(t, c), a = r(a, g), i = r(i, d), C = r(C, m);
    }
    return [ t, a, i, C ];
}

function i(r) {
    for (var n = "", t = 0; t < 4 * r.length; t++) n += "0123456789abcdef".charAt(r[t >> 2] >> t % 4 * 8 + 4 & 15) + "0123456789abcdef".charAt(r[t >> 2] >> t % 4 * 8 & 15);
    return n;
}

function C(r) {
    for (var n = 1 + (r.length + 8 >> 6), t = new Array(16 * n), o = 0; o < 16 * n; o++) t[o] = 0;
    for (o = 0; o < r.length; o++) t[o >> 2] |= (255 & r.charCodeAt(o)) << o % 4 * 8;
    return t[o >> 2] |= 128 << o % 4 * 8, t[16 * n - 2] = 8 * r.length, t;
}

function h(r) {
    for (var n = "", t = 0; t < r.length; t++) {
        var o = r.charCodeAt(t);
        o < 128 ? n += String.fromCharCode(o) : o < 2048 ? (n += String.fromCharCode(o >> 6 | 192),
            n += String.fromCharCode(63 & o | 128)) : o < 65536 ? (n += String.fromCharCode(o >> 12 | 224),
            n += String.fromCharCode(o >> 6 & 63 | 128), n += String.fromCharCode(63 & o | 128)) : (n += String.fromCharCode(o >> 18 | 240),
            n += String.fromCharCode(o >> 12 & 63 | 128), n += String.fromCharCode(o >> 6 & 63 | 128),
            n += String.fromCharCode(63 & o | 128));
    }
    return n;
}

function hexMD5(r){
    return i(a(C(h(r))));
}

function myGetTime() {
    return ""+new Date().getTime();
}

function http_builder_url(t, i) {
    if (void 0 === t || null == t || "" == t) return "";
    if (void 0 === i || null == i || "object" != (void 0 === i ? "undefined" : e(i))) return t;
    t += -1 != t.indexOf("?") ? "" : "?";
    for (var o in i) t += (-1 != t.indexOf("=") ? "&" : "") + o + "=" + encodeURIComponent(i[o]);
    return t;
}

function getsign(url) {
    t = new Date().getTime();
    return hexMD5(http_builder_url(url).replace("https://handler.1010pic.com", "") + "wxghy56@ue7y$r" + t);
    
}
// module.exports = {
//     hexMD5: function(r) {
//         return i(a(C(h(r))));
//     }
// };