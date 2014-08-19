nhn.husky.SE2_TimeStamper = jindo.$Class({
	$ON_EVENT_EDITING_AREA_KEYUP : function(weEvent) {
		var text = this.oApp.getContents();
		var div_len = document.getElementById("length");

		for (i = 0; i < text.length; i++) {
			var a = text.charCodeAt(i);
			if (a > 128) {
				var bytes = text.length * 2;
			} else if (a < 128) {
				var bytes = text.length * 1;
			}
		}

		div_len.innerHTML = bytes;
		if (bytes >= 4000) {
			alert("4000자 이하로 입력해주세요");
			this.oApp.exec("SET_CONTENTS", [ text.substring(0, 1998) ]);
			text = this.oApp.getContents();
			div_len = document.getElementById("length");
			bytes = text.length * 2;
			div_len.innerHTML = bytes;
		}
	}
});