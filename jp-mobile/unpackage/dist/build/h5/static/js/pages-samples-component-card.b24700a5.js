(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["pages-samples-component-card"],{"2e52":function(t,i,s){"use strict";s.r(i);var a=s("4d61"),e=s.n(a);for(var n in a)"default"!==n&&function(t){s.d(i,t,(function(){return a[t]}))}(n);i["default"]=e.a},"4d61":function(t,i,s){"use strict";Object.defineProperty(i,"__esModule",{value:!0}),i.default=void 0;var a={data:function(){return{isCard:!1}},methods:{IsCard:function(t){this.isCard=t.detail.value}}};i.default=a},"63f6":function(t,i,s){"use strict";s.r(i);var a=s("7169"),e=s("2e52");for(var n in e)"default"!==n&&function(t){s.d(i,t,(function(){return e[t]}))}(n);var c,v=s("f0c5"),u=Object(v["a"])(e["default"],a["b"],a["c"],!1,null,"11d26a50",null,!1,a["a"],c);i["default"]=u.exports},7169:function(t,i,s){"use strict";var a,e=function(){var t=this,i=t.$createElement,s=t._self._c||i;return s("v-uni-view",[s("cu-custom",{attrs:{bgColor:"bg-gradual-pink",isBack:!0}},[s("template",{attrs:{slot:"backText"},slot:"backText"},[t._v("返回")]),s("template",{attrs:{slot:"content"},slot:"content"},[t._v("卡片")])],2),s("v-uni-view",{staticClass:"cu-bar bg-white solid-bottom"},[s("v-uni-view",{staticClass:"action"},[s("v-uni-text",{staticClass:"cuIcon-titles text-orange"}),t._v("案例类卡片")],1),s("v-uni-view",{staticClass:"action"},[s("v-uni-switch",{class:t.isCard?"checked":"",attrs:{checked:!!t.isCard},on:{change:function(i){arguments[0]=i=t.$handleEvent(i),t.IsCard.apply(void 0,arguments)}}})],1)],1),s("v-uni-view",{staticClass:"cu-card case",class:t.isCard?"no-card":""},[s("v-uni-view",{staticClass:"cu-item shadow"},[s("v-uni-view",{staticClass:"image"},[s("v-uni-image",{attrs:{src:"https://ossweb-img.qq.com/images/lol/web201310/skin/big10006.jpg",mode:"widthFix"}}),s("v-uni-view",{staticClass:"cu-tag bg-blue"},[t._v("史诗")]),s("v-uni-view",{staticClass:"cu-bar bg-shadeBottom"},[s("v-uni-text",{staticClass:"text-cut"},[t._v("我已天理为凭，踏入这片荒芜，不再受凡人的枷锁遏制。我已天理为凭，踏入这片荒芜，不再受凡人的枷锁遏制。")])],1)],1),s("v-uni-view",{staticClass:"cu-list menu-avatar"},[s("v-uni-view",{staticClass:"cu-item"},[s("v-uni-view",{staticClass:"cu-avatar round lg",staticStyle:{"background-image":"url(https://ossweb-img.qq.com/images/lol/web201310/skin/big10006.jpg)"}}),s("v-uni-view",{staticClass:"content flex-sub"},[s("v-uni-view",{staticClass:"text-grey"},[t._v("正义天使 凯尔")]),s("v-uni-view",{staticClass:"text-gray text-sm flex justify-between"},[t._v("十天前"),s("v-uni-view",{staticClass:"text-gray text-sm"},[s("v-uni-text",{staticClass:"cuIcon-attentionfill margin-lr-xs"}),t._v("10"),s("v-uni-text",{staticClass:"cuIcon-appreciatefill margin-lr-xs"}),t._v("20"),s("v-uni-text",{staticClass:"cuIcon-messagefill margin-lr-xs"}),t._v("30")],1)],1)],1)],1)],1)],1)],1),s("v-uni-view",{staticClass:"cu-bar bg-white solid-bottom",class:t.isCard?"margin-top":""},[s("v-uni-view",{staticClass:"action"},[s("v-uni-text",{staticClass:"cuIcon-titles text-orange "}),t._v("动态类卡片")],1),s("v-uni-view",{staticClass:"action"},[s("v-uni-switch",{class:t.isCard?"checked":"",attrs:{checked:!!t.isCard},on:{change:function(i){arguments[0]=i=t.$handleEvent(i),t.IsCard.apply(void 0,arguments)}}})],1)],1),s("v-uni-view",{staticClass:"cu-card dynamic",class:t.isCard?"no-card":""},[s("v-uni-view",{staticClass:"cu-item shadow"},[s("v-uni-view",{staticClass:"cu-list menu-avatar"},[s("v-uni-view",{staticClass:"cu-item"},[s("v-uni-view",{staticClass:"cu-avatar round lg",staticStyle:{"background-image":"url(https://ossweb-img.qq.com/images/lol/web201310/skin/big10006.jpg)"}}),s("v-uni-view",{staticClass:"content flex-sub"},[s("v-uni-view",[t._v("凯尔")]),s("v-uni-view",{staticClass:"text-gray text-sm flex justify-between"},[t._v("2019年12月3日")])],1)],1)],1),s("v-uni-view",{staticClass:"text-content"},[t._v("折磨生出苦难，苦难又会加剧折磨，凡间这无穷的循环，将有我来终结！")]),s("v-uni-view",{staticClass:"grid flex-sub padding-lr",class:t.isCard?"col-3 grid-square":"col-1"},t._l(t.isCard?9:1,(function(i,a){return s("v-uni-view",{key:a,staticClass:"bg-img",class:t.isCard?"":"only-img",staticStyle:{"background-image":"url(https://ossweb-img.qq.com/images/lol/web201310/skin/big10006.jpg)"}})})),1),s("v-uni-view",{staticClass:"text-gray text-sm text-right padding"},[s("v-uni-text",{staticClass:"cuIcon-attentionfill margin-lr-xs"}),t._v("10"),s("v-uni-text",{staticClass:"cuIcon-appreciatefill margin-lr-xs"}),t._v("20"),s("v-uni-text",{staticClass:"cuIcon-messagefill margin-lr-xs"}),t._v("30")],1),s("v-uni-view",{staticClass:"cu-list menu-avatar comment solids-top"},[s("v-uni-view",{staticClass:"cu-item"},[s("v-uni-view",{staticClass:"cu-avatar round",staticStyle:{"background-image":"url(https://ossweb-img.qq.com/images/lol/img/champion/Morgana.png)"}}),s("v-uni-view",{staticClass:"content"},[s("v-uni-view",{staticClass:"text-grey"},[t._v("莫甘娜")]),s("v-uni-view",{staticClass:"text-gray text-content text-df"},[t._v("凯尔，你被自己的光芒变的盲目。")]),s("v-uni-view",{staticClass:"bg-grey padding-sm radius margin-top-sm  text-sm"},[s("v-uni-view",{staticClass:"flex"},[s("v-uni-view",[t._v("凯尔：")]),s("v-uni-view",{staticClass:"flex-sub"},[t._v("妹妹，你在帮他们给黑暗找借口吗?")])],1)],1),s("v-uni-view",{staticClass:"margin-top-sm flex justify-between"},[s("v-uni-view",{staticClass:"text-gray text-df"},[t._v("2018年12月4日")]),s("v-uni-view",[s("v-uni-text",{staticClass:"cuIcon-appreciatefill text-red"}),s("v-uni-text",{staticClass:"cuIcon-messagefill text-gray margin-left-sm"})],1)],1)],1)],1),s("v-uni-view",{staticClass:"cu-item"},[s("v-uni-view",{staticClass:"cu-avatar round",staticStyle:{"background-image":"url(https://ossweb-img.qq.com/images/lol/web201310/skin/big10006.jpg)"}}),s("v-uni-view",{staticClass:"content"},[s("v-uni-view",{staticClass:"text-grey"},[t._v("凯尔")]),s("v-uni-view",{staticClass:"text-gray text-content text-df"},[t._v("妹妹，如果不是为了飞翔，我们要这翅膀有什么用?")]),s("v-uni-view",{staticClass:"bg-grey padding-sm radius margin-top-sm  text-sm"},[s("v-uni-view",{staticClass:"flex"},[s("v-uni-view",[t._v("莫甘娜：")]),s("v-uni-view",{staticClass:"flex-sub"},[t._v("如果不能立足于大地，要这双脚又有何用?")])],1)],1),s("v-uni-view",{staticClass:"margin-top-sm flex justify-between"},[s("v-uni-view",{staticClass:"text-gray text-df"},[t._v("2018年12月4日")]),s("v-uni-view",[s("v-uni-text",{staticClass:"cuIcon-appreciate text-gray"}),s("v-uni-text",{staticClass:"cuIcon-messagefill text-gray margin-left-sm"})],1)],1)],1)],1)],1)],1)],1),s("v-uni-view",{staticClass:"cu-bar bg-white solid-bottom margin-top"},[s("v-uni-view",{staticClass:"action"},[s("v-uni-text",{staticClass:"cuIcon-titles text-orange "}),t._v("文章类卡片")],1),s("v-uni-view",{staticClass:"action"},[s("v-uni-switch",{class:t.isCard?"checked":"",attrs:{checked:!!t.isCard},on:{change:function(i){arguments[0]=i=t.$handleEvent(i),t.IsCard.apply(void 0,arguments)}}})],1)],1),s("v-uni-view",{staticClass:"cu-card article",class:t.isCard?"no-card":""},[s("v-uni-view",{staticClass:"cu-item shadow"},[s("v-uni-view",{staticClass:"title"},[s("v-uni-view",{staticClass:"text-cut"},[t._v("无意者 烈火焚身;以正义的烈火拔出黑暗。我有自己的正义，见证至高的烈火吧。")])],1),s("v-uni-view",{staticClass:"content"},[s("v-uni-image",{attrs:{src:"https://ossweb-img.qq.com/images/lol/web201310/skin/big10006.jpg",mode:"aspectFill"}}),s("v-uni-view",{staticClass:"desc"},[s("v-uni-view",{staticClass:"text-content"},[t._v("折磨生出苦难，苦难又会加剧折磨，凡间这无穷的循环，将有我来终结！真正的恩典因不完整而美丽，因情感而真诚，因脆弱而自由！")]),s("v-uni-view",[s("v-uni-view",{staticClass:"cu-tag bg-red light sm round"},[t._v("正义天使")]),s("v-uni-view",{staticClass:"cu-tag bg-green light sm round"},[t._v("史诗")])],1)],1)],1)],1)],1)],1)},n=[];s.d(i,"b",(function(){return e})),s.d(i,"c",(function(){return n})),s.d(i,"a",(function(){return a}))}}]);