window.__require = function t(e, r, o) {
function n(i, u) {
if (!r[i]) {
if (!e[i]) {
var f = i.split("/");
f = f[f.length - 1];
if (!e[f]) {
var p = "function" == typeof __require && __require;
if (!u && p) return p(f, !0);
if (c) return c(f, !0);
throw new Error("Cannot find module '" + i + "'");
}
i = f;
}
var a = r[i] = {
exports: {}
};
e[i][0].call(a.exports, function(t) {
return n(e[i][1][t] || t);
}, a, a.exports, t, e, r, o);
}
return r[i].exports;
}
for (var c = "function" == typeof __require && __require, i = 0; i < o.length; i++) n(o[i]);
return n;
}({
Game: [ function(t, e, r) {
"use strict";
cc._RF.push(e, "28b91NCRb9MOZdBWEMvyaTA", "Game");
var o, n = this && this.__extends || (o = function(t, e) {
return (o = Object.setPrototypeOf || {
__proto__: []
} instanceof Array && function(t, e) {
t.__proto__ = e;
} || function(t, e) {
for (var r in e) Object.prototype.hasOwnProperty.call(e, r) && (t[r] = e[r]);
})(t, e);
}, function(t, e) {
o(t, e);
function r() {
this.constructor = t;
}
t.prototype = null === e ? Object.create(e) : (r.prototype = e.prototype, new r());
}), c = this && this.__decorate || function(t, e, r, o) {
var n, c = arguments.length, i = c < 3 ? e : null === o ? o = Object.getOwnPropertyDescriptor(e, r) : o;
if ("object" == typeof Reflect && "function" == typeof Reflect.decorate) i = Reflect.decorate(t, e, r, o); else for (var u = t.length - 1; u >= 0; u--) (n = t[u]) && (i = (c < 3 ? n(i) : c > 3 ? n(e, r, i) : n(e, r)) || i);
return c > 3 && i && Object.defineProperty(e, r, i), i;
};
Object.defineProperty(r, "__esModule", {
value: !0
});
var i = cc._decorator, u = i.ccclass, f = (i.property, function(t) {
n(e, t);
function e() {
return null !== t && t.apply(this, arguments) || this;
}
e.prototype.onLoad = function() {};
e.prototype.start = function() {};
e.prototype.update = function() {};
return c([ u ], e);
}(cc.Component));
r.default = f;
cc._RF.pop();
}, {} ]
}, {}, [ "Game" ]);