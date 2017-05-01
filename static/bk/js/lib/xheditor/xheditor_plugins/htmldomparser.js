function isHiddenNode(e){if(e.nodeName.toLowerCase()=="title")return!1;if(window.getComputedStyle){try{var t=window.getComputedStyle(e,null);if(t.getPropertyValue&&t.getPropertyValue("display")=="none")return!0}catch(n){}return!1}}var HTMLParser=function(e,t,n){n=n||{};var r=n.nodesToIgnore||[],i=n.parseHiddenNodes||"false",s=e.childNodes;for(var o=0;o<s.length;o++)try{var u=!1;for(var a=0;a<r.length;a++)if(s[o].nodeName.toLowerCase()==r[a]){u=!0;break}if(u||!i&&isHiddenNode(s[o]))continue;if(s[o].nodeName.toLowerCase()!="#text"&&s[o].nodeName.toLowerCase()!="#comment"){var f=[];if(s[o].hasAttributes()){var l=s[o].attributes;for(var c=0;c<l.length;c++){var h=l.item(c);f.push({name:h.nodeName,value:h.nodeValue})}}if(t.start)if(s[o].hasChildNodes()){t.start(s[o].nodeName,f,!1);if(s[o].nodeName.toLowerCase()=="pre"||s[o].nodeName.toLowerCase()=="code")t.chars(s[o].innerHTML);else if(s[o].nodeName.toLowerCase()=="iframe"||s[o].nodeName.toLowerCase()=="frame"){if(s[o].contentDocument&&s[o].contentDocument.documentElement)return HTMLParser(s[o].contentDocument.documentElement,t,n)}else s[o].hasChildNodes()&&HTMLParser(s[o],t,n);t.end&&t.end(s[o].nodeName)}else t.start(s[o].nodeName,f,!0)}else s[o].nodeName.toLowerCase()=="#text"?t.chars&&t.chars(s[o].nodeValue):s[o].nodeName.toLowerCase()=="#comment"&&t.comment&&t.comment(s[o].nodeValue)}catch(p){console.log("error while parsing node: "+s[o].nodeName.toLowerCase())}};