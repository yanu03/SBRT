requires("uiplugin.popup");

/**
 * gcm 객체에는 전체 Scope에서 공유되는 Global 전역 변수, 상수, 공통 함수를 정의한다.
 * 
 * gcm 객체는 WFrame Scope이 고려될 필요가 없고, 업무 개발자가 호출할 필요한 공통 함수나 속성만을 생성한다. 
 * gcm 객체는 WFrame Scope별로 생성되지 않고, 전역 객체로 1개만 생성된다.
 * 
 * @version 3.0
 * @author tracom
 * @type gcm
 * @class gcm
 * @namespace gcm
 */

var gcm = {
	// 서버 통신 서비스 호출을 위한 Context Path
	CONTEXT_PATH : "",

	// 서버 통신 서비스 호출을 위한 Service Url (Context Path 이하 경로)
	SERVICE_URL : "",
	
	/* GRID_INFO 정보
	CUR_ROW_INDEX : -1, //현재의 ROW_INDEX
	OLD_ROW_INDEX : -1, //이전 ROW_INDEX
	ERR_ROW_INDEX : -1, //에러 발생시 ROW_INDEX
	keyColumn:null,
	focusColumn:null,
	keyValue:null,
	name:null
	*/
	GRID_INFO : [], 

	CUR_PROGRAM_AUTH : "" ,
	
	// 서버 통신 기본 모드 ( "asynchronous" / "synchronous")
	DEFAULT_OPTIONS_MODE : "asynchronous",

	// 서버 통신 기본 미디어 타입
	DEFAULT_OPTIONS_MEDIATYPE : "application/json",

	// 통신 상태 코드
	MESSAGE_CODE : {
		STATUS_ERROR : "E",
		STATUS_SUCCESS : "S",
		STATUS_SAVE : "SAVE",
		STATUS_WARNING : "W"
	},
	isSavePoup : false,
	
	// 공통 코드 저장을 위한 DataList 속성 정보
	DATA_PREFIX : "dlt_commonDtl",

	COMMON_CODE_INFO : {
		LABEL : "DL_CD_NM",
		VALUE : "DL_CD",
		FILED_ARR : [ "CO_CD", "DL_CD", "DL_CD_NM" ]
	},
	
	// 시스템 코드 저장을 위한 DataList 속성 정보
	SYSTEM_PREFIX : "dlt_systemCode",

	SYSTEM_CODE_INFO : {
		LABEL : "SYSTEM_NM",
		VALUE : "SYSTEM_VALUE",
		FILED_ARR : [ "SYSTEM_CD", "SYSTEM_VALUE", "SYSTEM_NM" ]
	},

	// 유효성 검사 상태 정보 저장
	valStatus : {
		isValid : true,
		objectType : "", // 유효성 검사를 수행하는 컴포넌트 타입 : gridView, group
		objectName : "",
		columnId : "",
		rowIndex : 0,
		message : ""
	},

	// 업무 화면 오픈 Frame Mode 설정 ("iframe", "wframe")
	FRAME_MODE : "wframe",

	// 메세지 알림 콜백 Function 정보 저장
	CB_FUNCTION_MANAGER : {
		cbFuncIdx : 0, // 정보 저장 Index Key
		cbFuncSave : {}
	// 정보 저장 객체
	},
	
	// 화면 유형
	DISP_TYPE : {
		SINGLE_GRID : "SINGLE_GRID", //단일 그리드
		DUAL_GRID : "DUAL_GRID", //복수 그리드(메인,서브 둘 다 CRUD)
		DUAL_GRID2 : "DUAL_GRID2", //복수 그리드(메인 조회,서브는 CRUD)
		DUAL_GRID3 : "DUAL_GRID3", //복수 그리드(메인 조회,서브1 CRUD, 서브2 조회)
		SINGLE_GRID_FORM : "SINGLE_GRID_FORM", //단일 그리드 와 폼 
		DUAL_GRID_FORM : "DUAL_GRID_FORM", //복수 그리드와 폼(메인 CRUD 폼, 서브 조회)
		DUAL_GRID_FORM2 : "DUAL_GRID_FORM2" //복수 그리드(메인 조회, 서브 CRUD와 폼)	
	},
	// 버튼
	BTN : {
		SEARCH : { nm : "SEARCH", value : "SCH_AH", class : "search", str : "조회", cbFnc:{}}, //검색
		ADD : { nm : "ADD", value : "SAV_AH", class : "add", str : "추가", cbFnc:{}}, //추가
		DEL : { nm : "DEL", value : "SAV_AH", class : "del", str : "삭제", cbFnc:{}}, //삭제
		CNL : { nm : "CNL", value : "SAV_AH", class : "clr", str : "취소", cbFnc:{}}, //취소
		SAVE : { nm : "SAVE", value : "SAV_AH", class : "save", str : "저장", cbFnc:{}}, //저장
		EXL_I : { nm : "EXL_I", value : "IEX_AH", class : "exli", str : "엑셀업로드", cbFnc:{}}, //엑셀업로드
		EXL : { nm : "EXL", value : "EXL_AH", class : "exl", str : "엑셀", cbFnc:{}}, //엑셀다운로드
		EXL_F : { nm : "EXL_F", value : "GEX_AH", class : "exlf", str : "엑셀양식", cbFnc:{}}, //엑셀양식
		RESERV : { nm : "RESERV", value : "FN3_AH", class : "reserv", str : "예약", cbFnc:{}}, //예약
		RESERV_CNL : { nm : "RESERV_CNL", value : "FN3_AH", class : "reservcnl", str : "예약취소", cbFnc:{}}, //예약
		PLAY : { nm : "PLAY", value : "FN4_AH", class : "play", str : "실행", cbFnc:{}}, //실행
		CONFIRM_YN : { nm : "CONFIRM_YN", value : "FN5_AH", class : "confirmyn", str : "확정", cbFnc:{}}, //확정
		SETTING : { nm : "SETTING", value : "FN6_AH", class : "setting", str : "설정", cbFnc:{}}, //설정
		INIT : { nm : "INIT", value : "FN7_AH", class : "init", str : "초기화", cbFnc:{}}, //초기화
		HELP : { nm : "HELP", value : "HELP_AH", class : "help", str : "도움말", cbFnc:{}}, //도움말
		CLOSE : { nm : "CLOSE", value : "AUTH_CHECK", class : "close", str : "닫기", cbFnc:{}} //닫기
	},
	
	// [단축키] 이벤트 설정 객체
	shortcutEvent : {
		loadingEvent : "Y",
		keydownEvent : function(e) {
			if (gcm.shortcutEvent.loadingEvent == "Y") {
				var exTag = [ "INPUT" ];
				var activeTag = document.activeElement.tagName;
				if (exTag.indexOf(activeTag) == -1) {
					console.log(activeTag + "########################");
					gcm.shortcutEvent["checkEvent"].apply(this, [ e ]);
				}
			}
		},
		checkEvent : function(e) {
			try {
				var lastKey = e.which || e.keyCode;
				var complexKey = "";

				if (e.ctrlKey && e.altKey) {
					complexKey = "ctrlAltKey";
				} else if (e.ctrlKey && e.shiftKey) {
					complexKey = "ctrlShiftKey";
				} else if (e.altKey && e.shiftKey) {
					complexKey = "altShiftKey";
				} else {
					if (e.altKey) {
						complexKey = "altKey";
					} else if (e.ctrlKey) {
						complexKey = "ctrlKey";
					} else if (e.shiftKey) {
						complexKey = "shiftKey";
					} else {
						complexKey = "singleKey";
					}
				}

				// (Ctrl, Alt, Shift)가 아닌 lastKey가 인식될 경우
				if (e.key != "Control" && e.key != "Alt" && e.key != "Shift") {
					var codeKey = (complexKey != "") ? (complexKey + "_" + lastKey) : lastKey;
					var browser = "";

					// 브라우저에 따라 Key 값이 변형됨.
					if (browser == "FF") {
						// 파이어폭스 인경우.
						lastKey = e.key.toUpperCase();
						gcm.shortcutEvent.runEvent.apply(e, [ complexKey, codeKey, lastKey ]);
					} else {
						if (!isNaN(Number(lastKey))) {
							if (lastKey >= 112 && lastKey <= 123) {
								var f_number = [ "F1", "F2", "F3", "F4", "F5", "F6", "F7", "F8", "F9", "F10", "F11", "F12" ];
								lastKey = f_number[lastKey - 112];
							} else if (lastKey == 9) {
								lastKey = "TAB";
							} else if (!isNaN(Number(e.key)) && Number(e.key) > -1 && Number(e.key) < 10) {
								lastKey = e.key;
							} else {
								lastKey = String.fromCharCode(lastKey).toUpperCase();
							}

							gcm.shortcutEvent.runEvent.apply(e, [ complexKey, lastKey ]);
						}
					}
				}
			} catch (e) {

			}
		},

		// 단축키 실행 함수.
		runEvent : function(complex, eventKey) {
			try {
				var checkShortcut = {};
				var layout = $p.top().scwin.getLayoutId();
				var programCd = "";

				var activeWindowInfo = com.getActiveWindowInfo();
				var findframe = activeWindowInfo["window"]; // 단축키가 감지된 프레임

				var searchEvent = com.dataListFilter(gcm.shortcutComp.getID(), "PROG_CD == '" + activeWindowInfo["programCd"]
						+ "' && COMPLEX_KEY == '" + complex + "' && LAST_KEY == '" + eventKey + "'", false);
				if (typeof searchEvent == "undefined" || searchEvent.length == 0) {
					searchEvent = com.dataListFilter(gcm.shortcutComp.getID(), "PROG_CD == 'TOP' && COMPLEX_KEY == '" + complex
							+ "' && LAST_KEY == '" + eventKey + "'", false);
				}

				if (typeof searchEvent != "undefined" && searchEvent.length > 0) {
					var shortuctObj = searchEvent[0];
					if (shortuctObj["EVENT_TYPE"] === "F") {
						if (shortuctObj["EVENT_YN"] == "Y") {
							// 이벤트 사용허용 일경우.
							var funcTokenArr = shortuctObj["EVENT_TARGET"].split(".");
							var runFunction = findframe;
							if (funcTokenArr.length > 0) {
								for (var i = 0; i < funcTokenArr.length; i++) {
									runFunction = runFunction[funcTokenArr[i].trim()];
								}
							} else {
								findRunFunction = false;
							}

							if (typeof runFunction == "function") {
								runFunction();
							}
						}
					} else if (shortuctObj["EVENT_TYPE"] === "B") {
						if (shortuctObj["EVENT_YN"] == "Y") {
							// 이벤트 사용허용 일경우.
							var runComponent = findframe.$p.getComponentById(shortuctObj["EVENT_TARGET"].trim());
							if (runComponent) {
								runComponent.trigger("click");
							}
						}
					}
				}
				gcm.shortcutComp.removeColumnFilterAll();
			} catch (e) {

			}
		},

		// 단축키 등록 추가 함수.
		addEvent : function(object) {
			var successFlag = false;
			try {
				var programCd = (object["PROG_CD"] || "");
				var keyCodeObj = gcm.shortcutEvent._keyToken(object.shortCutKey.toUpperCase());
				var eventTarget = (object["EVENT_TARGET"] || "");
				var eventName = (object["EVENT_NAME"] || "");
				var eventDetail = (object["EVENT_DETAIL"] || "");
				var eventType = (object["EVENT_TYPE"].toUpperCase() == "B" || object["EVENT_TYPE"].toUpperCase() == "BUTTON") ? "B" : "F";
				var eventYn = (object["EVENT_YN"] || "Y");
				if (programCd == "" || eventTarget == "") {
					return false;
				} else {
					var isKey = com.dataListFilter(gcm.shortcutComp.getID(), "PROG_CD == '" + object["PROG_CD"]
							+ "' && COMPLEX_KEY == '" + keyCodeObj["COMPLEX_KEY"] + "' && LAST_KEY == '" + keyCodeObj["LAST_KEY"] + "'",
							false);
					if (isKey.length > 0) {
						var index = gcm.shortcutComp.getRealRowIndex(0);
						gcm.shortcutComp.setRowJSON(insertIdx, {
							"PROG_CD" : programCd,
							"COMPLEX_KEY" : keyCodeObj["COMPLEX_KEY"],
							"LAST_KEY" : keyCodeObj["LAST_KEY"],
							"EVENT_TARGET" : eventTarget,
							"EVENT_NAME" : eventName,
							"EVENT_DETAIL" : eventDetail,
							"EVENT_TYPE" : eventType,
							"EVENT_YN" : eventYn
						}, true);
					} else {
						var insertIdx = gcm.shortcutComp.insertRow();
						gcm.shortcutComp.setRowJSON(insertIdx, {
							"PROG_CD" : programCd,
							"COMPLEX_KEY" : keyCodeObj["COMPLEX_KEY"],
							"LAST_KEY" : keyCodeObj["LAST_KEY"],
							"EVENT_TARGET" : eventTarget,
							"EVENT_NAME" : eventName,
							"EVENT_DETAIL" : eventDetail,
							"EVENT_TYPE" : eventType,
							"EVENT_YN" : eventYn
						}, true);
					}
					gcm.shortcutComp.removeColumnFilterAll();
					return true;
				}
			} catch (e) {
			}

			return WebSquare.util.getBoolean(successFlag);
		},

		_keyToken : function(keyCode) {
			try {
				var rtnVal = {
					"COMPLEX_KEY" : "",
					"LAST_KEY" : ""
				};
				var token = keyCode.split("+");
				// 단축키 등록.
				if (token.length > 2) {
					var firstKey = token[0].toUpperCase();
					var secondKey = token[1].toUpperCase();
					var lastKey = isNaN(Number(token[2])) ? token[2] : "NUM" + token[2];
					if (firstKey == "ALT") {
						rtnVal["COMPLEX_KEY"] = "altShiftKey";
						rtnVal["LAST_KEY"] = lastKey;
					} else if (firstKey == "CTRL") {
						if (secondKey == "SHIFT") {
							rtnVal["COMPLEX_KEY"] = "ctrlShiftKey";
							rtnVal["LAST_KEY"] = lastKey;
						} else {
							rtnVal["COMPLEX_KEY"] = "ctrlAltKey";
							rtnVal["LAST_KEY"] = lastKey;
						}
					}
				} else if (token.length == 2) {
					var firstKey = token[0].toUpperCase();
					var lastKey = isNaN(Number(token[1])) ? token[1] : "NUM" + token[1];
					if (firstKey == "CTRL" || firstKey == "CTRLKEY") {
						rtnVal["COMPLEX_KEY"] = "ctrlKey";
						rtnVal["LAST_KEY"] = lastKey;
					} else if (firstKey == "ALT" || firstKey == "ALTKEY") {
						rtnVal["COMPLEX_KEY"] = "altKey";
						rtnVal["LAST_KEY"] = lastKey;
					} else if (firstKey == "SHIFT" || firstKey == "SHIFTKEY") {
						rtnVal["COMPLEX_KEY"] = "shiftKey";
						rtnVal["LAST_KEY"] = lastKey;
					} else if (firstKey == "ALTSHIFTKEY") {
						rtnVal["COMPLEX_KEY"] = "altShiftKey";
						rtnVal["LAST_KEY"] = lastKey;
					} else if (firstKey == "CTRLSHIFTKEY") {
						rtnVal["COMPLEX_KEY"] = "ctrlShiftKey";
						rtnVal["LAST_KEY"] = lastKey;
					} else if (firstKey == "CTRLALTKEY") {
						rtnVal["COMPLEX_KEY"] = "ctrlAltKey";
						rtnVal["LAST_KEY"] = lastKey;
					} else {
						rtnVal["COMPLEX_KEY"] = "singleKey";
						rtnVal["LAST_KEY"] = lastKey;
					}
				} else {
					var lastKey = isNaN(Number(token[0])) ? token[0] : "NUM" + token[0];
					rtnVal["COMPLEX_KEY"] = "singleKey";
					rtnVal["LAST_KEY"] = lastKey;
				}
				return rtnVal;
			} catch (e) {

			}
		},

		// 단축키 등록 삭제 함수.
		delEvent : function(keyCode, options) {
			var rtnVal = true;
			try {
				if (keyCode.lastIndexOf("+") > 0) {
					keyCode = keyCode.toUpperCase();
					var _idx = keyCode.lastIndexOf("+");
					var lastKey = keyCode.slice(_idx + 1);
					var complex = keyCode.slice(0, _idx);
					var complexArr = "";
					if (!isNaN(Number(lastKey))) {
						lastKey = "NUM" + lastKey;
					}

					if (complex == "ALT" || complex == "ALTKEY") {
						complexArr = "altKey";
					} else if (complex == "CTRL" || complex == "CTRLKEY") {
						complexArr = "ctrlKey";
					} else if (complex == "SHIFT" || complex == "SHIFTKEY") {
						complexArr = "shiftKey";
					} else if (complex == "ALT+SHIFT" || complex == "ALTSHIFTKEY") {
						complexArr = "altShiftKey";
					} else if (complex == "CTRL+SHIFT" || complex == "CTRLSHIFTKEY") {
						complexArr = "ctrlShiftKey";
					} else if (complex == "CTRL+ALT" || complex == "CTRLALTKEY") {
						complexArr = "ctrlAltKey";
					} else if (complex == "SINGLE" || complex == "SINGLEKEY") {
						complexArr = "singleKey";
					}

					var isKey = com.dataListFilter(gcm.shortcutComp.getID(), "PROG_CD == '" + options["PROG_CD"]
							+ "' && COMPLEX_KEY == '" + complexArr + "' && LAST_KEY == '" + lastKey + "'", false);
					if (isKey.length > 0) {
						gcm.shortcutComp.removeRow(0);
					}
					gcm.shortcutComp.removeColumnFilterAll();
				}
			} catch (e) {
				rtnVal = false;
			}
			return rtnVal;
		}
	}
};

/**
 * 유효성 검사 실패에 대한 Alert 메시지 창이 닫힌 후에 수행되는 콜백 함수이다.
 * 
 * @date 2021.04.15
 * @private
 * @memberOf
 * @author tracom
 * @returns
 */
gcm._groupValidationCallback = function() {
	
	if ((gcm.valStatus.objectName !== "") && (gcm.valStatus.isValid === false)) {
		var obj = $p.getComponentById(gcm.valStatus.objectName);
		if (gcm.valStatus.objectType === "gridView") {
			obj.setFocusedCell(gcm.valStatus.rowIndex, gcm.valStatus.columnId);
		} else if (gcm.valStatus.objectType === "group") {
			obj.focus();
		} else if (gcm.valStatus.objectType === "gridForm") {
			obj.setFocusedCell(gcm.valStatus.rowIndex, gcm.valStatus.columnId);
			if(gcm.valStatus.focusTableID !== null){
				$p.getComponentById(gcm.valStatus.focusTableID).focus();				
			}
		}
	}
};

/**
 * submission의 공통 설정에서 사용.
 * submission 통신 직전 호출.
 * return true일 경우 통신 수행, return false일 경우 통신 중단
 *
 * @date 2021.11.11
 * @private
 * @param {Object} sbmObj 서브미션 객체
 * @memberOf
 * @author tracom
 * @return {Boolean} true or false
 */
gcm._sbm_preSubmission = function(sbmObj) {
	return true;
};

/**
 * 모든 submission의 defaultCallback - com.sbm_errorHandler 보다 먼저 수행됨. (400 Error)
 * config.xml에 설정
 * 
 * @private
 * @date 2021.11.15
 * @param {Object} resObj responseData 객체
 * @param {Object} subObj Submission 객체
 * @memberOf
 * @author tracom
 */
gcm._sbm_defCallbackSubmission = function(resObj, subObj) {

	var scopeCom = gcm._getScope(subObj).com;

	// server와 연결을 할 수 없을 경우 responseStatusCode가 0으로 발생.
	if (resObj.responseStatusCode == 0) {
		var detailStr = "HTTP STATUS INFO";
		detailStr += resObj.responseStatusCode;
		detailStr += "URI:";
		detailStr += resObj.resourceUri;

		var msgObj = {
			statusCode : "E",
			errorCode : "E9998",
			message : "서버와 연결할 수 없습니다. 자세한 내용은 관리자에게 문의하시기 바랍니다.",
			messageDetail : detailStr
		};

		scopeCom.resultMsg(msgObj);

		return false;
	}

	var rsJSON = resObj.responseJSON || null;
	if (rsJSON && rsJSON.rsMsg) {
		scopeCom.resultMsg(rsJSON.rsMsg);
	}
};

/**
 * submission중 에러가 발생한 경우 호출되는 함수 - 서버 오류(500 error)
 * 
 * @date 2021.11.15
 * @private
 * @param {Object} resObj responseData 객체
 * @memberOf
 * @author tracom
 */
gcm._sbm_errorHandler = function(resObj) {
	var scopeCom = gcm._getScope(resObj.id).com;

	var detailStr = "HTTP STATUS INFO";
	detailStr += resObj.responseReasonPhrase;
	detailStr += " ";
	detailStr += resObj.responseStatusCode;
	detailStr += "URI:";
	detailStr += resObj.resourceUri;
	detailStr += resObj.responseBody;

	var msgObj = {
		statusCode : "E",
		errorCode : "E9998",
		message : "서버 오류입니다. 자세한 내용은 관리자에게 문의하시기 바랍니다.",
		messageDetail : detailStr
	};

	scopeCom.resultMsg(msgObj);
};

/**
 * 특정 컴포넌트가 속한 WFrame Scope을 반환한다.
 * 
 * @date 2021.06.11
 * @private
 * @param {Object} 컴포넌트 객체 또는 ID(WFrame Scope 경로를 포함한 Full Path Id)
 * @memberOf
 * @author tracom
 */
gcm._getScope = function(comObj) {
	if (typeof comObj === "string") {
		var scopeObj = $p.getComponentById(comObj);
		if (scopeObj !== null) {
			return scopeObj.getScopeWindow();
		}
	} else {
		return comObj.getScopeWindow();
	}
}

/**
 * 각 WFrame Scope별로 공유되는 Scope 전역 변수와 공통 함수를 정의한다.
 * 
 * com 객체는 WFrame Scope 업무 개발자가 호출해야할 공통 함수나 속성을 정의한다.
 * com 객체는 WFrame Scope 별로 생성되기 때문에 com 객체 내에 정의된 함수에서의 선언된 $p 객체는 
 * 해당 함수를 호출한 화면의 WFrame Scope 내의 $p를 참조하게 된다.
 * 
 * @version 3.0
 * @author tracom
 * @type com
 * @class com
 * @namespace com
 */
var com = {
	// Message Box ID 생성을 위한 순번
	MESSAGE_BOX_SEQ : 1
};

/**
 * 메인 화면에서 업무 화면을 오픈하는 Frame Mode 정보를 반환한다.
 * 
 * @date 2021.11.14
 * @memberOf com
 * @author tracom
 */
com.getFrameMode = function() {
	return gcm.FRAME_MODE;
};

/**
 * 사용자의 권한에 따른 화면 컴포넌트 제어를 한다.
 * 
 * @date 2021.12.07
 * @memberOf com
 * @author tracom
 */
com._setProgramAuthority = function() {

	var param = com.getParameter();
	if ((typeof param !== "undefined") && (typeof param.menuCode !== "undefined") && (param.menuCode.trim() !== "")) {
		var menuCd = param.menuCode;
		var menuInfoList = $p.top().wfm_header.getWindow().dlt_menu.getMatchedJSON("MENU_CD", menuCd);

		if (menuInfoList.length > 0) {
			var programAuthorityList = $p.top().wfm_header.getWindow().dlt_programAuthority.getMatchedJSON("PROG_CD",
					menuInfoList[0].PROG_CD);

			if (programAuthorityList.length > 0) {
				gcm.CUR_PROGRAM_AUTH = programAuthorityList[0];
				
				var objArr = WebSquare.util.getChildren($p.getFrame(), {
					excludePlugin : "group trigger textbox output calendar image span",
					recursive : true
				});

				for (var i = 0; i < objArr.length; i++) {
					if ((objArr[i].getPluginName() === "anchor") || (objArr[i].getPluginName() === "trigger")) {
						if (objArr[i].getOriginalID().indexOf("btn_search") > -1) {
							if (gcm.CUR_PROGRAM_AUTH.AUTH_CHECK !== "Y") {
								objArr[i].hide();
							}
						} else if (objArr[i].getOriginalID().indexOf("btn_add") > -1) {
							if (gcm.CUR_PROGRAM_AUTH.SAV_AH !== "Y") {
								objArr[i].hide();
							}
						} else if (objArr[i].getOriginalID().indexOf("btn_cancel") > -1) {
							if (gcm.CUR_PROGRAM_AUTH.SAV_AH !== "Y") {
								objArr[i].hide();
							}
						} else if (objArr[i].getOriginalID().indexOf("btn_save") > -1) {
							if (gcm.CUR_PROGRAM_AUTH.SAV_AH !== "Y") {
								objArr[i].hide();
							}
						} else if (objArr[i].getOriginalID().indexOf("btn_excel") > -1) {
							if (gcm.CUR_PROGRAM_AUTH.EXL_AH !== "Y") {
								objArr[i].hide();
							}
						}
					}
				}
			}
		}
	}
}

/**
 * 로그인한 사용자가 시스템 관리자 인지의 여부를 반환한다.
 * 
 * @date 2021.12.01
 * @memberOf com
 * @author tracom
 */
com.isAdmin = function() {
	scwin.isAdmin = $p.top().wfm_header.getWindow().dma_defInfo.get("IS_ADMIN");
	if (scwin.isAdmin === "Y") {
		return true;
	} else {
		return false;
	}
}

/**
 * 로그인한 사용자의 ID(사원번호)를 반환한다.
 * 
 * @date 2021.12.01
 * @memberOf com
 * @author tracom
 */
com.getLoginUserId = function() {
	return $p.top().wfm_header.getWindow().dma_defInfo.get("USER_ID");
}

/**
 * Submission를 실행합니다.
 *
 * @date 2021.01.19
 * @param {Object} options com.createSubmission의 options 참고
 * @param {Object} requestData 요청 데이터
 * @param {Object} obj 전송중 disable시킬 컴퍼넌트
 * @memberOf com
 * @author tracom
 * @example
 * var searchCodeCoOption = {
 *		 id : "sbm_searchCodeCo",
 *		 action : "serviceId=CD0001&action=R",
 *		 target : 'data:json,{"id":"dlt_codeGrp","key":"data"}',
 *		 submitDoneHandler : scwin.searchCodeCoCallback, isShowMeg : false };
 * com.executeSubmission_dynamic(searchCodeCoOption);
 */
com.executeSubmission_dynamic = function(options, requestData, obj) {
	var submissionObj = $p.getSubmission(options.id);

	if (submissionObj === null) {
		com.createSubmission(options);
		submissionObj = $p.getSubmission(options.id);
	} else {
		$p.deleteSubmission(options.id);
		com.createSubmission(options);
		submissionObj = $p.getSubmission(options.id);
	}

	com.executeSubmission(submissionObj, requestData, obj);
};

/**
 * Submission 객체를 동적으로 생성한다.
 *
 * @date 2021.11.30
 * @param {Object} options Submission 생성 옵션 JSON 객체
 * @param {String} options.id submission 객체의 ID. 통신 모듈 실행 시 필요.
 * @param {String} options.ref 서버로 보낼(request) DataCollection의 조건 표현식.(조건에 때라 표현식이 복잡하다) 또는 Instance Data의 XPath.
 * @param {String} options.target 서버로 응답(response) 받은 데이터가 위치 할 DataCollection의 조건 표현식. 또는 Instance Data의 XPath.
 * @param {String} options.action 통신 할 서버 측 URI.(브라우저 보안 정책으로 crossDomain은 지원되지 않는다.)
 * @param {String} options.method [default: get, post, urlencoded-post] 
 * - get : 파라메타를 url에 붙이는 방식 (HTML과 동일).
 * - post : 파라메타를 body 구간에 담는 방식 (HTML과 동일) 
 * - urlencoded-post : urlencoded-post.
 * @param {String} options.mediatype [default: application/xml, text/xml, application/json, application/x-www-form-urlencoded]
 * application/x-www-form-urlencoded 웹 form 방식(HTML방식). application/json : json 방식. application/xml : XML 방식. text/xml : xml방식
 * (두 개 차이는 http://stackoverflow._com/questions/4832357 참조)
 * @param {String} options.mode [default: synchronous, synchronous] 서버와의 통신 방식.  asynchronous:비동기식.  synchronous:동기식
 * @param {String} options.encoding [default: utf-8, euc-kr, utf-16] 서버 측 encoding 타입 설정 (euc-kr/utf-16/utf-8)
 * @param {String} options.replace [default: none, all, instance] action으로부터 받은 response data를 적용 구분 값.
 *   - all : 문서 전체를 서버로부터 온 응답데이터로 교체.  
 *   - instance : 해당되는 데이터 구간.  
 *   - none : 교체안함.
 * @param {String} options.processMsg submission 통신 중 보여줄 메세지.
 * @param {String} options.errorHandler submission오류 발생 시 실행 할 함수명.
 * @param {String} options.customHandler submssion호출 시 실행 할 함수명.
 * @param {requestCallback} options.submitHandler {script type="javascript" ev:event="xforms-submit"} 에 대응하는 함수.
 * @param {requestCallback} options.submitDoneHandler {script type="javascript" ev:event="xforms-submit-done"} 에 대응하는 함수
 * @param {requestCallback} options.submitErrorHandler {script type="javascript" ev:event="xforms-submit-error"} 에 대응하는 함수
 * @memberOf com
 * @author tracom
 * @example
 * com.createSubmission(options);
 */
com.createSubmission = function(options) {
	var ref = options.ref || "";
	var target = options.target || "";
	var action = gcm.CONTEXT_PATH + gcm.SERVICE_URL + options.action; // ajax 요청주소
	var mode = options.mode || gcm.DEFAULT_OPTIONS_MODE; // asynchronous(default)/synchronous
	var mediatype = options.mediatype || gcm.DEFAULT_OPTIONS_MEDIATYPE; // application/x-www-form-urlencoded
	var method = (options.method || "post").toLowerCase(); // get/post/put/delete
	var processMsg = options.processMsg || "";
	var instance = options.instance || "none";

	var submitHandler = (typeof options.submitHandler === "function") ? options.submitHandler
			: ((typeof options.submitHandler === "string") ? $p.id + options.submitHandler : "");
	var submitDoneHandler = (typeof options.submitDoneHandler === "function") ? options.submitDoneHandler
			: ((typeof options.submitDoneHandler === "string") ? $p.id + options.submitDoneHandler : "");
	var submitErrorHandler = (typeof options.submitErrorHandler === "function") ? options.submitErrorHandler
			: ((typeof options.submitErrorHandler === "string") ? $p.id + options.submitErrorHandler : "");

	var isShowMeg = false;
	var resJson = null;

	if ((options.isProcessMsg === true) && (processMsg === "")) {
		processMsg = "해당 작업을 처리중입니다";
	}

	if (typeof options.isShowMeg !== "undefined") {
		isShowMeg = options.isShowMeg;
	}

	var submissionObj = {
		"id" : options.id, // submission 객체의 ID. 통신 모듈 실행 시 필요.
		"ref" : ref, // 서버로 보낼(request) DataCollection의 조건 표현식.(조건에 때라 표현식이 복잡하다) 또는 Instance Data의 XPath.
		"target" : target, // 서버로 응답(response) 받은 데이터가 위치 할 DataCollection의 조건 표현식. 또는 Instance Data의 XPath.
		"action" : action, // 통신 할 서버 측 URI.(브라우저 보안 정책으로 crossDomain은 지원되지 않는다.)
		"method" : method, // [default: post, get, urlencoded-post] get:파라메타를 url에 붙이는 방식 (HTML과 동일).
		// post:파라메타를 body 구간에 담는 방식 (HTML과 동일). urlencoded-post:urlencoded-post.
		"mediatype" : mediatype, // application/json
		"encoding" : "UTF-8", // [default: utf-8, euc-kr, utf-16] 서버 측 encoding 타입 설정 (euc-kr/utf-16/utf-8)
		"mode" : mode, // [default: synchronous, synchronous] 서버와의 통신 방식. asynchronous:비동기식. synchronous:동기식
		"processMsg" : processMsg, // submission 통신 중 보여줄 메세지.
		"submitHandler" : submitHandler,
		"submitDoneHandler" : submitDoneHandler,
		"submitErrorHandler" : submitErrorHandler
	};

	$p.createSubmission(submissionObj);
};

/**
 * 서버 통신 확장 모듈, Submission를 실행합니다.
 * 
 * @date 2021.11.30
 * @param {Object} sbmObj submission 객체
 * @param {Object} requestData [Default : null, JSON, XML] 요청 데이터로 submission에 등록된 ref를 무시하고 현재의 값이 할당된다.
 * @param {Object} compObj [Default : null] 전송중 disable시킬 컴퍼넌트
 * @memberOf com
 * @author tracom
 * @example 
 * // Submission ID : sbm_init 존재할 경우 
 * com.executeSubmission(sbm_Init); 
 * // return 예시) sbm_init 통신 실행
 * 
 * // Submission ID : sbm_init 존재하지 않을 경우 
 * com.executeSubmission(sbm_Init); 
 * // return 예시) alert - submission 객체[sbm_init]가 존재하지 않습니다.
 */
com.executeSubmission = function(sbmObj, requestData, compObj) {

	if (sbmObj.action.indexOf(gcm.CONTEXT_PATH) < -1) {
		sbmObj.action = gcm.CONTEXT_PATH + sbmObj.action;
	}
	$p.executeSubmission(sbmObj, requestData, compObj);
};

/**
 * 서버에서 전송한 통신 결과 코드를 반환한다.
 * 화면에 정의한 submission의 submitdone이벤트에서 호출하여 사용한다.
 * 
 * @date 2021.07.29
 * @param {Object} e submission 후 callback의 상태값
 * @memberOf com
 * @author tracom
 * @return {String} 상태 코드
 * @example
 * // 통신결과 코드가 있을 경우
 * com.getResultCode(e);
 * // return 예시) E || S || W
 *
 * // 통신결과 코드가 없을 경우
 * com.getResultCode(e);
 * // return 예시) 웹스퀘어5 로그창 - 결과 상태 메세지가 없습니다.: com.getResultCode
 */
com.getResultCode = function(e) {
	var rsCode = gcm.MESSAGE_CODE.STATUS_ERROR;
	try {
		rsCode = e.responseJSON.rsMsg.statusCode;
	} catch (ex) {
		$p.log("결과 상태 메세지가 없습니다.: com.getResultCode");
	}

	return rsCode;
};

/**
 * statusCode값에 따라 message를 출력한다.
 * 
 * @private
 * @date 2021.08.09
 * @param {Object} resultData 상태코드값 및 메시지가 담긴 JSON.
 * @param {String} resultData.message 메시지
 * @param {String} resultData.statusCode 상태코드값
 * @memberOf com
 * @author tracom
 */
com.resultMsg = function(resultData) {
	resultData.message = resultData.message || "";
	var msgCode = gcm.MESSAGE_CODE;

	switch (resultData.statusCode) {
	case msgCode.STATUS_ERROR:
		if (resultData.errorCode == "E0001") {
			com.alert(/*resultData.message + */" 로그인 화면으로 이동하겠습니다.", "com.goHome");
		} else if (resultData.errorCode == "E9998") { // HTTP ERROR ex)404,500,0
			resultData.message = resultData.message;
		} else if (resultData.errorCode == "E9999") { // business logic error
			resultData.message = resultData.message;
		}
		break;
	case "N":
		resultData.statusCode = msgCode.STATUS_ERROR;
		resultData.message = "서버가 정지된 상태입니다. 자세한 내용은 관리자에게 문의하시기 바랍니다.";
		break;
	default:
	}

	if (typeof $p.top().scwin.setResultMessage == "function") {
		$p.top().scwin.setResultMessage(resultData);
	}
};

/**
 * 코드성 데이터와 컴포넌트의 nodeSet(아이템 리스트)연동 기능을 제공한다.
 * code별로 JSON객체를 생성하여 array에 담아 첫번째 파라메터로 넘겨준다.
 *
 * @date 2021.04.13
 * @param {Object} codeOptions {"code" : "코드넘버", "compID" : "적용할 컴포넌트명"}
 * @param {requestCallback} callbackFunc 콜백 함수
 * @memberOf com
 * @author tracom
 * @example
 * var codeOptions = [ { code : "00001", compID : "sbx_Duty" },
 *					 { code : "00002", compID : "sbx_Postion" },
 *					 { code : "00021", compID : "sbx_JoinClass" },
 *					 { code : "00005", compID : "sbx_CommCodePart1, sbx_CommCodePart2"},
 *					 { code : "00024", compID :"grd_CommCodeSample:JOB_CD"} ];
 *	 com.setCommonDtl(codeOptions);
 */
com.setCommonDtl = function(codeOptions, callbackFunc) {
	var codeOptionsLen = 0;

	if (codeOptions) {
		codeOptionsLen = codeOptions.length;
	} else {
		$p.log("=== com.setCommonDtl Parameter Type Error ===\nex) com.setCommonDtl([{\"code:\":\"04\",\"compID\":\"sbx_Gender\"}],\"scwin.callbackFunction\")\n===================================");
		return;
	}

	var i, j, codeObj, dltId, dltIdArr = [], paramCode = "", compArr, compArrLen, tmpIdArr;
	var dataListOption = _getCodeDataListOptions(gcm.COMMON_CODE_INFO.FILED_ARR);

	for (i = 0; i < codeOptionsLen; i++) {
		codeObj = codeOptions[i];

		try {
			dltId = gcm.DATA_PREFIX + codeObj.code;
			if (typeof $p.top().scwin.commonDtlList[dltId] === "undefined") {
				dltIdArr.push(dltId);

				if (i > 0) {
					paramCode += ",";
				}
				paramCode += codeObj.code;
				dataListOption.id = dltId;
				$p.data.create(dataListOption); // 동일한 id의 DataCollection이 존재할 경우, 삭제 후 재생성함
			} else {
				dataListOption.id = dltId;
				$p.data.create(dataListOption);
				var dataListObj = $p.getComponentById(dataListOption.id);
				dataListObj.setJSON(com.getJSON($p.top().scwin.commonDtlList[dltId]));
			}
			
			if (codeObj.compID) {
				compArr = (codeObj.compID).replaceAll(" ", "").split(",");
				compArrLen = compArr.length;
				for (j = 0; j < compArrLen; j++) {
					tmpIdArr = compArr[j].split(":");
					// 기본 컴포넌트에 대한 Node Setting 설정
					if (tmpIdArr.length === 1) {
						var comp = $p.getComponentById(tmpIdArr[0]);
						comp.setNodeSet("data:" + dltId, gcm.COMMON_CODE_INFO.LABEL, gcm.COMMON_CODE_INFO.VALUE);
						// gridView 컴포넌트에 대한 Node Setting 설정
					} else {
						var gridObj = $p.getComponentById(tmpIdArr[0]);
						gridObj.setColumnNodeSet(tmpIdArr[1], "data:" + dltId, gcm.COMMON_CODE_INFO.LABEL, gcm.COMMON_CODE_INFO.VALUE);
					}
				}
			}
		} catch (ex) {
			$p.log("com.setCommonDtl Error");
			$p.log(JSON.stringify(codeObj));
			$p.log(ex);
			continue;
		}
	}

	var searchCodeCoOption = {
		id : "sbm_searchCode",
		action : "/common/selectCodeList",
		target : "data:json," + com.strSerialize(dltIdArr),
		isShowMeg : false
	};

	searchCodeCoOption.submitDoneHandler = function(e) {
		
		for (codeGrpDataListId in e.responseJSON) {
			if (codeGrpDataListId.indexOf(gcm.DATA_PREFIX) > -1) {
				$p.top().scwin.commonDtlList[codeGrpDataListId] = com.strSerialize(e.responseJSON[codeGrpDataListId]);
			}
		}

		if (typeof callbackFunc === "function") {
			callbackFunc();
		}
	}

	if (paramCode !== "") {
		com.executeSubmission_dynamic(searchCodeCoOption, {
			"dma_commonDtl" : {
				"CO_CD" : paramCode,
				"DATA_PREFIX" : gcm.DATA_PREFIX
			}
		});
	} else {
		if (typeof callbackFunc === "function") {
			callbackFunc();
		}
	}

	// dataList를 동적으로 생성하기 위한 옵션 정보를 반환한다.
	function _getCodeDataListOptions(infoArr) {
		var option = {
			"type" : "dataList",
			"option" : {
				"baseNode" : "list",
				"repeatNode" : "map"
			},
			"columnInfo" : []
		};

		for ( var idx in infoArr) {
			option.columnInfo.push({
				"id" : infoArr[idx]
			});
		}
		return option;
	}
	;
};

/**
 * prefix 기준으로 코드성 데이터와 컴포넌트의 nodeSet(아이템 리스트)연동 기능을 제공한다.
 * code별로 JSON객체를 생성하여 array에 담아 첫번째 파라메터로 넘겨준다.
 *
 * @param {Object} codeOptions {"code" : "코드넘버", "compID" : "적용할 컴포넌트명"}
 * @param {prefix} 코드를 분리하기 위한 prefix
 * @param {requestCallback} callbackFunc 콜백 함수
 * @memberOf com
 * @author tracom
 * @example
 * var codeOptions = [ { code : "00001", compID : "sbx_Duty" },
 *					 { code : "00002", compID : "sbx_Postion" },
 *					 { code : "00021", compID : "sbx_JoinClass" },
 *					 { code : "00005", compID : "sbx_CommCodePart1, sbx_CommCodePart2"},
 *					 { code : "00024", compID :"grd_CommCodeSample:JOB_CD"} ];
 *	 com.setCommonSubDtl(codeOptions);
 */
com.setCommonSubDtl = function(codeOptions, prefix, callbackFunc) {
	var codeOptionsLen = 0;

	if (codeOptions) {
		codeOptionsLen = codeOptions.length;
	} else {
		$p.log("=== com.setCommonDtl Parameter Type Error ===\nex) com.setCommonDtl([{\"code:\":\"04\",\"compID\":\"sbx_Gender\"}],\"scwin.callbackFunction\")\n===================================");
		return;
	}

	var i, j, codeObj, dltId, dltIdArr = [], paramCode = "", compArr, compArrLen, tmpIdArr;
	var dataListOption = _getCodeDataListOptions(gcm.COMMON_CODE_INFO.FILED_ARR);

	for (i = 0; i < codeOptionsLen; i++) {
		codeObj = codeOptions[i];

		try {
			dltId = prefix + gcm.DATA_PREFIX + codeObj.code;
			if (typeof $p.top().scwin.commonDtlList[dltId] === "undefined") {
				dltIdArr.push(dltId);

				if (i > 0) {
					paramCode += ",";
				}
				paramCode += codeObj.code;
				dataListOption.id = dltId;
				$p.data.create(dataListOption); // 동일한 id의 DataCollection이 존재할 경우, 삭제 후 재생성함
			}
			else
			{
				dataListOption.id = dltId;
				$p.data.create(dataListOption);
				var dataListObj = $p.getComponentById(dataListOption.id);
				dataListObj.setJSON(com.getJSON($p.top().scwin.commonDtlList[dltId]));
			}

			if (codeObj.compID) {
				compArr = (codeObj.compID).replaceAll(" ", "").split(",");
				compArrLen = compArr.length;
				
				//dataListOption.id = dltId+"2";
				//$p.data.create(dataListOption);
				
				for (j = 0; j < compArrLen; j++) {
					tmpIdArr = compArr[j].split(":");
					// 기본 컴포넌트에 대한 Node Setting 설정
					if (tmpIdArr.length === 1) {
						var comp = $p.getComponentById(tmpIdArr[0]);
						/*for(var x=0; x<comp.allItemArr.length; x++){
							if(comp.allItemArr[x].value.lastIndexOf(prefix) === 0){
								
							}
						}*/
						comp.setNodeSet("data:" + dltId, gcm.COMMON_CODE_INFO.LABEL, gcm.COMMON_CODE_INFO.VALUE);
						
						// gridView 컴포넌트에 대한 Node Setting 설정
					} else {
						var gridObj = $p.getComponentById(tmpIdArr[0]);
						gridObj.setColumnNodeSet(tmpIdArr[1], "data:" + dltId, gcm.COMMON_CODE_INFO.LABEL, gcm.COMMON_CODE_INFO.VALUE);
					}
				}
			}
		} catch (ex) {
			$p.log("com.setCommonDtl Error");
			$p.log(JSON.stringify(codeObj));
			$p.log(ex);
			continue;
		}
	}
	
	
	var searchCodeCoOption = {
		id : "sbm_searchCode",
		action : "/common/selectCodeList2",
		target : "data:json," + com.strSerialize(dltIdArr),
		isShowMeg : false
	};

	searchCodeCoOption.submitDoneHandler = function(e) {
		for (codeGrpDataListId in e.responseJSON) {
			if (codeGrpDataListId.indexOf(gcm.DATA_PREFIX) > -1) {
				$p.top().scwin.commonDtlList[codeGrpDataListId] = com.strSerialize(e.responseJSON[codeGrpDataListId]);
			}
		}

		if (typeof callbackFunc === "function") {
			callbackFunc();
		}
	}

	//데이터 가져오는 submission
	if (paramCode !== "") {
		com.executeSubmission_dynamic(searchCodeCoOption, {
			"dma_commonDtl" : {
				"CO_CD" : paramCode,
				"DL_CD" : prefix,
				"DATA_PREFIX" : gcm.DATA_PREFIX
			}
		});
	} else {
		if (typeof callbackFunc === "function") {
			callbackFunc();
		}
	}

	// dataList를 동적으로 생성하기 위한 옵션 정보를 반환한다.
	function _getCodeDataListOptions(infoArr) {
		var option = {
			"type" : "dataList",
			"option" : {
				"baseNode" : "list",
				"repeatNode" : "map"
			},
			"columnInfo" : []
		};

		for ( var idx in infoArr) {
			option.columnInfo.push({
				"id" : infoArr[idx]
			});
		}
		return option;
	}
	;
};

/**
 * 시스템 코드 데이터와 컴포넌트의 nodeSet(아이템 리스트)연동 기능을 제공한다.
 * code별로 JSON객체를 생성하여 array에 담아 첫번째 파라메터로 넘겨준다.
 *
 * @date 2021.04.13
 * @param {Object} systemOptions {"code" : "코드넘버", "compID" : "적용할 컴포넌트명"}
 * @param {requestCallback} callbackFunc 콜백 함수
 * @memberOf com
 * @author tracom
 * @example
 * var systemOptions = [ { code : "00001", compID : "sbx_Duty" },
 *					 { code : "00002", compID : "sbx_Postion" },
 *					 { code : "00021", compID : "sbx_JoinClass" },
 *					 { code : "00005", compID : "sbx_CommCodePart1, sbx_CommCodePart2"},
 *					 { code : "00024", compID :"grd_CommCodeSample:JOB_CD"} ];
 *	 com.setCommonDtl(codeOptions);
 */
com.setSystemCode = function(systemOptions, callbackFunc) {
	var systemOptionsLen = 0;
	
	if (systemOptions) {
		systemOptionsLen = systemOptions.length;
	} else {
		$p.log("=== com.setSystemCode Parameter Type Error ===\nex) com.setSystemCode([{\"code:\":\"04\",\"compID\":\"sbx_Gender\"}],\"scwin.callbackFunction\")\n===================================");
		return;
	}

	var i, j, systemObj, dltId, dltIdArr = [], paramCode = "", compArr, compArrLen, tmpIdArr;
	var dataListOption = _getSystemDataListOptions(gcm.SYSTEM_CODE_INFO.FILED_ARR);

	for (i = 0; i < systemOptionsLen; i++) {
		systemObj = systemOptions[i];

		try {
			dltId = gcm.SYSTEM_PREFIX + systemObj.code;
			if (typeof $p.top().scwin.systemCodeList[dltId] === "undefined") {
				dltIdArr.push(dltId);

				if (i > 0) {
					paramCode += ",";
				}
				paramCode += systemObj.code;
				dataListOption.id = dltId;
				$p.data.create(dataListOption); // 동일한 id의 DataCollection이 존재할 경우, 삭제 후 재생성함
			} else {
				dataListOption.id = dltId;
				$p.data.create(dataListOption);
				var dataListObj = $p.getComponentById(dataListOption.id);
				dataListObj.setJSON(com.getJSON($p.top().scwin.systemCodeList[dltId]));
			}
			
			if (systemObj.compID) {
				compArr = (systemObj.compID).replaceAll(" ", "").split(",");
				compArrLen = compArr.length;
				for (j = 0; j < compArrLen; j++) {
					tmpIdArr = compArr[j].split(":");
					// 기본 컴포넌트에 대한 Node Setting 설정
					if (tmpIdArr.length === 1) {
						var comp = $p.getComponentById(tmpIdArr[0]);
						comp.setNodeSet("data:" + dltId, gcm.SYSTEM_CODE_INFO.LABEL, gcm.SYSTEM_CODE_INFO.VALUE);
						// gridView 컴포넌트에 대한 Node Setting 설정
					} else {
						var gridObj = $p.getComponentById(tmpIdArr[0]);
						gridObj.setColumnNodeSet(tmpIdArr[1], "data:" + dltId, gcm.SYSTEM_CODE_INFO.LABEL, gcm.SYSTEM_CODE_INFO.VALUE);
					}
				}
			}
		} catch (ex) {
			$p.log("com.setCommonDtl Error");
			$p.log(JSON.stringify(systemObj));
			$p.log(ex);
			continue;
		}
	}

	var searchSystemGrpOption = {
		id : "sbm_searchSystem",
		action : "/common/selectSystemList",
		target : "data:json," + com.strSerialize(dltIdArr),
		isShowMeg : false
	};

	searchSystemGrpOption.submitDoneHandler = function(e) {
		
		for (codeGrpDataListId in e.responseJSON) {
			if (codeGrpDataListId.indexOf(gcm.DATA_PREFIX) > -1) {
				$p.top().scwin.systemCodeList[codeGrpDataListId] = com.strSerialize(e.responseJSON[codeGrpDataListId]);
			}
		}

		if (typeof callbackFunc === "function") {
			callbackFunc();
		}
	}

	if (paramCode !== "") {
		com.executeSubmission_dynamic(searchSystemGrpOption, {
			"dma_systemCode" : {
				"SYSTEM_CD" : paramCode,
				"DATA_PREFIX" : gcm.SYSTEM_PREFIX
			}
		});
	} else {
		if (typeof callbackFunc === "function") {
			callbackFunc();
		}
	}

	// dataList를 동적으로 생성하기 위한 옵션 정보를 반환한다.
	function _getSystemDataListOptions(infoArr) {
		var option = {
			"type" : "dataList",
			"option" : {
				"baseNode" : "list",
				"repeatNode" : "map"
			},
			"columnInfo" : []
		};

		for ( var idx in infoArr) {
			option.columnInfo.push({
				"id" : infoArr[idx]
			});
		}
		return option;
	}
	;
};

/**
 * GridView Row 삭제를 위한 CheckBox 동작을 세팅한다.
 * GridView에 삭제용 CheckBox가 있을 경우 onPageLoad 이벤트에서 com.setGridViewDelCheckBox 함수를 호출한다.
 * 이 함수가 정상 동작하려면 GridView의 Delete 처리용 CheckBox의 ColumnId와 Header Id를 "chk"로 설정해야 한다.
 * 
 * @date 2021.11.26
 * @memberOf com
 * @author tracom
 * @param {Array} gridViewObj GridView 객체 배열
 * @example
 * com.setGridViewDelCheckBox(grd_OrganizationBasic);
 * com.setGridViewDelCheckBox([grd_Menu, grd_MenuAccess]);
 */
com.setGridViewDelCheckBox = function(gridViewObjArr) {
	try {
		if (com.getObjectType(gridViewObjArr) === "array") {
			for (idx in gridViewObjArr) {
				setGridViewDelCheckBox(gridViewObjArr[idx]);
			}
		} else {
			setGridViewDelCheckBox(gridViewObjArr);
		}

		function setGridViewDelCheckBox(gridViewObj) {
			gridViewObj.bind("oncellclick", function(row, col) {
				if (col == 0) {
					var dltObj = com.getGridViewDataList(this);
					var realRowIndex = this.getRealRowIndex(row);
					var newValue = dltObj.getCellData(realRowIndex, col);
					com._setGridViewRowCheckBox(this, realRowIndex, newValue === "1" ? true : false);
				}
			});
			gridViewObj.bind("onheaderclick", function(headerId) {
				if (headerId == "chk") {
					var newValue = this.getHeaderValue(headerId);
					var dltObj = com.getGridViewDataList(this);
					var rowCount = dltObj.getRowCount();

					dltObj.setBroadcast(false);
					for (var rowIdx = 0; rowIdx < rowCount; rowIdx++) {
						com._deleteGridViewRow(this, rowIdx, newValue);
					}
					dltObj.setBroadcast(true, true);
				}
			});
		}
	} catch (e) {
		$p.log("[com.setGridViewDelCheckBox] Exception :: " + e.message);
	}
};

com._setGridViewRowCheckBox = function(gridViewObj, rowIndex, newValue) {
	var rowIndexArr = gridViewObj.getChildrenRowIndexArray(rowIndex);
	var dltObj = com.getGridViewDataList(gridViewObj);

	for ( var idx in rowIndexArr) {
		var childRowIndexArr = gridViewObj.getChildrenRowIndexArray(rowIndexArr[idx]);

		if (childRowIndexArr.length > 0) {
			com._setGridViewRowCheckBox(gridViewObj, rowIndexArr[idx], newValue);
		} else {
			com._deleteGridViewRow(gridViewObj, rowIndexArr[idx], newValue);
		}
	}

	com._deleteGridViewRow(gridViewObj, rowIndex, newValue);
};

com._deleteGridViewRow = function(gridViewObj, rowIndex, newValue) {
	gridViewObj.setCellChecked(rowIndex, "chk", newValue);
	var dltObj = com.getGridViewDataList(gridViewObj);

	if (newValue) {
		var rowStatus = dltObj.getRowStatus(rowIndex);
		if (rowStatus == "C") {
			dltObj.removeRow(rowIndex);
		} else {
			dltObj.deleteRow(rowIndex);
		}
	} else {
		dltObj.undoRow(rowIndex);
	}
}

/**
 * GridView와 바인딩된 DataList 객체를 반환한다.
 *
 * @date 2021.01.11
 * @param {Object} gridViewObj 바인딩 된 DataList가 존재하는지 검증할 GridView 객체
 * @memberOf com
 * @author tracom
 * @return {Object} 바인딩 된 DataList 객체 반환 (바인된 객체가 없을 경우 null 반환)
 * @example
 * // 바인딩 되어있는 경우
 * com.getGridViewDataList(grd_First);
 * // return 예시) "dlt_first"
 *
 * // 바인딩 되어있지 않은 경우
 * com.getGridViewDataList(grd_First);
 * // return 예시) undefined
 */
com.getGridViewDataList = function(gridViewObj) {
	var dataListId = gridViewObj.getDataList();

	if (dataListId !== "") {
		var dataList = $p.getComponentById(dataListId);
		if ((typeof dataList === "undefined") || (dataList === null)) {
			$p.log("DataList(" + dataListId + ")를 찾을 수 없습니다.");
			return null;
		} else {
			return dataList;
		}
	} else {
		$p.log(gridViewObj.getID() + "는 DataList가 세팅되어 있지 않습니다.");
		return null;
	}
};

/**
 * 삭제된 데이터를 제외한 인덱스 얻기
 *
 * @date 2021.01.11
 * @param {Object} grid 대상 grid
 * @param {int} index 범위 index
 * @memberOf com
 * @author tracom
 * @return {int} 삭제된 데이터 제외한 index
 */
com.getGridDispIndex = function(grid, index) {
	var data = com.getGridViewDataList(grid);
	var dispIndex = 0;
	for(var i = 0; i < index; i++) { //노선의 노드 순번을 그리드 순서대로 재 할당함.
		if(data.getRowStatus(i)!="D"){
			dispIndex++;
		}
	}
	return dispIndex;
};

/**
 * 그리드에서 삭제된 데이터를 제외한 데이터 가져오기
 *
 * @date 2021.01.11
 * @param {Object} grid 대상 grid
 * @memberOf com
 * @author tracom
 * @return {Object} retData 삭제 제외된 데이터 리스트
 */
com.getGridDispJsonData = function(grid) {
	var data = com.getGridViewDataList(grid);
	var rowData = data.getAllJSON();
	var retData = [];
	for(var i = 0; i < rowData.length; i++) { //노선의 노드 순번을 그리드 순서대로 재 할당함.
		if(data.getRowStatus(i)!="D"){
			rowData[i].realIndex = i;
			retData.push(rowData[i]);
		}
	}
	return retData;
};

/* 삭제된 데이터를 제외한 데이터 가져오기
*
* @date 2021.01.11
* @param {Object} data 대상 data
* @memberOf com
* @author tracom
* @return {Object} retData 삭제 제외된 데이터 리스트
*/
com.getGridDispJsonData2 = function(data) {
	var rowData = data.getAllJSON();
	var retData = [];
	for(var i = 0; i < rowData.length; i++) { //노선의 노드 순번을 그리드 순서대로 재 할당함.
		if(data.getRowStatus(i)!="D"){
			rowData[i].realIndex = i;
			retData.push(rowData[i]);
		}
	}
	return retData;
};

/**
 * 화면 인덱스 기준으로 실제 인덱스 얻기
 *
 * @date 2021.01.11
 * @param {Object} grid 대상 grid
 * @param {int} index 범위 index
 * @memberOf com
 * @author tracom
 * @return {int} 삭제된 데이터 제외한 index
 */
com.getGridRealIndex = function(grid, index) {
	var data = com.getGridDispJsonData(grid);
	return data.getAllJSON()[index].realIndex;
};

/**
 * 특정 컴포넌트에 바인된 DataList나 DataMap의 컬럼 이름을 반환한다.
 * 
 * @date 2021.01.15
 * @memberOf com
 * @param {Object} comObj 컴포넌트 객체
 * @return {String} 컬럼명
 */
com.getColumnName = function(comObj) {
	try {
		if ((typeof comObj.getRef) === "function") {
			var ref = comObj.getRef();
			var refArray = ref.substring(5).split(".");
			var dataCollectionName = refArray[0];
			var columnId = refArray[1];

			if ((typeof refArray !== "undefined") && (refArray.length === 2)) {
				var dataCollection = comObj.getScopeWindow().$p.getComponentById(dataCollectionName);
				var dataType = dataCollection.getObjectType().toLowerCase();
				if (dataType === "datamap") {
					return dataCollection.getName(columnId);
				} else if (dataType === 'datalist') {
					return dataCollection.getColumnName(columnId);
				}
			} else {
				return "";
			}
		}
	} catch (e) {
		$p.log("[com.getColumnName] Exception :: " + e.message);
	} finally {
		dataCollection = null;
	}
};

/**
 * 특정 컴포넌트에 바인된 DataList나 DataMap의 정보를 반환한다.
 * 
 * @date 2021.01.15
 * @memberOf com
 * @param {Object} comObj callerObj 컴포넌트 객체
 * @returns {Object} dataCollection정보
 */
com.getDataCollection = function(comObj) {
	try {
		if ((typeof comObj !== "undefined") && (typeof comObj.getRef === "function")) {
			var ref = comObj.options.ref;
			if ((typeof ref !== "undefined") && (ref !== "")) {
				var refArray = ref.substring(5).split(".");
				if ((typeof refArray !== "undefined") && (refArray.length === 2)) {
					var dataObjInfo = {};
					dataObjInfo.runtimeDataCollectionId = comObj.getScope().id + "_" + refArray[0];
					dataObjInfo.dataColletionId = refArray[0];
					dataObjInfo.columnId = refArray[1];
					return dataObjInfo;
				} else {
					return null;
				}
			} else {
				return null;
			}
		}
	} catch (e) {
		console.log("[com.getDataCollection] Exception :: " + e.message);
	} finally {
		dataCollection = null;
	}
};

/**
 * DataCollection 객체의 변경된 데이터가 있는지 검사한다.
 * 
 * @date 2021.01.16
 * @memberOf com
 * @param {Object} dcObjArr DataCollection 또는 배열
 * @author tracom
 * @returns {Boolean} 검사결과 (true or false)
 */
com.checkModified = function(dcObjArr) {
	if (com.getObjectType(dcObjArr) === "array") {
		for ( var dcObj in dcObjArr) {
			if (com.getObjectType(dcObj) === "object") {
				if (checkModfied(dcObj) === false) {
					return false;
				}
			}
		}
	} else if (com.getObjectType(dcObjArr) === "object") {
		return checkModfied(dcObj);
	}

	return true;

	function checkModfied(dcObj) {
		if ((typeof dcObj !== "undefined") && (typeof dcObj !== null)) {
			var modifiedIndex = dcObj.getModifiedIndex();
			if (modifiedIndex.length === 0) {
				com.alert("변경된 데이터가 없습니다.");
				return false;
			} else {
				return true;
			}
		} else {
			return true;
		}
	}
};

/**
 * 엑셀 다운로드 옵션을 설정하고 확장자 별로 다른 함수(downLoadCSV || downLoadExcel)를 호출한다.
 *
 * @date 2021.11.16
 * @param {Object} grdObj GridView Object
 * @param {Array} options JSON형태로 저장된 그리드의 엑셀 다운로드 옵션
 * @param {Array} infoArr 그리드에 대한 내용을 추가로 다른 셀에 표현하는 경우 사용하는 배열
 * @memberOf com
 * @author tracom
 * @example
 * var gridId = "grd_AdvancedExcel";
 * var infoArr = {};
 * var options = {
 *	 fileName : "gridDataDownLoad.xlsx" //[defalut: excel.xlsx] 다운로드하려는 파일의 이름으로 필수 입력 값 (지원하는 타입 => xls, xlsx, csv)
 * };
 * com.gridDataDownLoad( gridId, options, infoArr);
 * //return 예시) common.js의 다른 함수(downLoadCSV, downLoadExcel)로 이동하거나 alert 발생
 */
com.gridDataDownLoad = function(grdObj, options, infoArr) {
	if (options == undefined)
		options = {};
	if (infoArr == undefined)
		infoArr = {};
	var fileName = options.fileName;

	if (fileName == undefined || fileName.length == 0) {
		var fileName = "excel_download";
		var dataCollectionName = grdObj.getDataList();
		if (dataCollectionName != null && typeof dataCollectionName != "undefined" && dataCollectionName != "") {
			var dataCollection = $p.getComponentById(dataCollectionName);
			var name = dataCollection.element.getAttribute("name")
			if (typeof name != "undefined" && name != "")
				fileName = name;
		}
		var fileType = "xlsx"
		if (options.fileType == undefined || options.fileType == "") {
			fileName = fileName + ".xlsx";
		} else {
			fileName = fileName + "." + options.fileType;
		}
		options.fileName = fileName;
	}

	fileName = fileName.toLowerCase();
	if (options.useProvider == "true") {
		options.dataProvider = "com.inswave.template.provider.ExcelDown";
	}
	if (options.useSplitProvider == "true") {
		options.splitProvider = "com.inswave.template.provider.ExcelSplitDown";
	}

	if (options.useProvider || options.useSplitProvider) {
		var colCnt = grdObj.getColumnCount();
		var columnsIDArr = new Array();

		for (var i = 0; i < colCnt; i++) {
			if (grdObj.getColumnVisible(i)) {
				var isRemoveCol = false;
				if (typeof options.excludeColumns != "undefined" && options.excludeColumns != null && options.excludeColumns.length > 0) {
					for (var k = 0; k < options.excludeColumns.length; k++) {
						if (grdObj.getColumnID(i) == options.excludeColumns[k]) {
							isRemoveCol = true;
							break;
						}
					}
				}
				if (isRemoveCol) {
					continue;
				}
				columnsIDArr.push(grdObj.getColumnID(i));
			}
		}

		var xmlDoc = WebSquare.xml.parse(options.providerRequestXml, false);
		WebSquare.xml.setValue(xmlDoc, "data/keyMap", columnsIDArr.join(","));
		options.providerRequestXml = WebSquare.xml.serialize(xmlDoc);
		delete options.useProvider;
		delete options.useSplitProvider;
	}

	if (fileName.indexOf(".csv") > -1) {
		com.downLoadCSV(grdObj, options);
	} else if (fileName.indexOf(".xlsx") > -1 || fileName.indexOf(".xls") > -1) {
		com.downLoadExcel(grdObj, options, infoArr);
	} else {
		com.alert("알수없는 파일 형식입니다");
	}
};

/**
 * 포커된 grid와 매핑된 frm의 focus 처리
 *
 * @date 2021.11.16
 * @param {grdObj} 대상 GridView Object
 * @param {frm} 대상 form
 * @param {index} 대상 grid의 현재 focus index
 * @param {focusColumn} focus column 명
 * @param {editMode} grid의 mode를 editMode로 할지
 * @memberOf com
 * @author tracom
 */
com.focusGridFrm = function(grid,frm, index, focusColumn, editMode){
	grid.setFocusedCell(index, focusColumn, editMode);
	
	var objArr = WebSquare.util.getChildren(frm, {
		excludePlugin : "group trigger textbox output calendar image span anchor pageInherit wframe itemTable",
		recursive : true
	});
	
	
	for (var objIdx in objArr) {
		var refId = objArr[objIdx].options.ref;

		if ((typeof refId == "undefined")) continue;
		// 확장자 명만 추출한 후 소문자로 변경
		var objRefId = refId.substring(refId.lastIndexOf('.')+1, refId.length);

	
		if (objRefId == gcm.valStatus.columnId) {
			$p.getComponentById(objArr[objIdx].getID()).focus();	
			break;
		}
		
	}

};

/**
 * 설정된 옵션으로 엑셀을 다운로드 한다.
 *
 * @date 2021.11.30
 * @param {Object} grdObj GridView 객체
 * @param {Object} options JSON형태로 저장된 그리드의 엑셀 다운로드 옵션
 * @param {String} options.fileName					[defalut: excel.xlsx] 다운로드하려는 파일의 이름으로 필수 입력 값 <br>지원하는 타입 => xls, xlsx
 * @param {String} [options.sheetName]				[defalut: sheet] excel의 sheet의 이름
 * @param {String} [options.type]					[defalut: 0] type 0 => 실제 데이터 <br>1 => 눈에 보이는 데이터<br>  2 => 들어가 있는 data 그대로(filter무시 expression 타입의 셀은 나오지 않음)
 * @param {String} [options.removeColumns]			[defalut: 없음] 다운로드시 excel에서 삭제하려는 열의 번호(여러 개일 경우 ,로 구분)
 * @param {String} [options.removeHeaderRows]		[defalut: 없음]	다운로드시 excel에서 삭제하려는 Header의 row index(여러 개일 경우 ,로 구분)
 * @param {String} [options.foldColumns]				[defalut: 없음] 다운로드시 excel에서 fold하려는 열의 번호(여러 개일 경우 ,로 구분)
 * @param {Number} [options.startRowIndex]			excel 파일에서 그리드의 데이터가 시작되는 행의 번호(헤더 포함)
 * @param {Number} [options.startColumnIndex]		excel 파일에서 그리드의 데이터가 시작되는 열의 번호(헤더 포함)
 * @param {String} [options.headerColor]				excel 파일에서 그리드의 header부분의 색
 * @param {String} [options.headerFontName]			[defalut: 없음] excel파일에서 그리드의 header부분의 font name
 * @param {String} [options.headerFontSize]			excel 파일에서 그리드의 header부분의 font size
 * @param {String} [options.headerFontColor]			excel 파일에서 그리드의 header부분의 font색
 * @param {String} [options.bodyColor]				excel 파일에서 그리드의 body부분의 색
 * @param {String} [options.bodyFontName]			[defalut: 없음] excel파일에서 그리드의 body부분의 font name
 * @param {String} [options.bodyFontSize]			excel 파일에서 그리드의 body부분의 font size
 * @param {String} [options.bodyFontColor]			excel 파일에서 그리드의 body부분의 font색
 * @param {String} [options.subTotalColor]			[defalut: #CCFFCC] excel파일에서 그리드의 subtotal부분의 색
 * @param {String} [options.subTotalFontName]		[defalut: 없음] excel파일에서 그리드의 subtotal부분의 font name
 * @param {String} [options.subTotalFontSize]		[defalut: 10] excel파일에서 그리드의 subtotal부분의 font size
 * @param {String} [options.subTotalFontColor]		[defalut: 없음] excel파일에서 그리드의 subtotal부분의 font색
 * @param {String} [options.footerColor]				[defalut: #008000] excel파일에서 그리드의 footer부분의 색
 * @param {String} [options.footerFontName]			[defalut: 없음] excel파일에서 그리드의 footer부분의 font name
 * @param {String} [options.footerFontSize]			[defalut: 10] excel파일에서 그리드의 footer부분의 font size
 * @param {String} [options.footerFontColor]			[defalut: 없음] excel파일에서 그리드의 footer부분의 font색
 * @param {String} [options.oddRowBackgroundColor]	excel 파일에서 그리드 body의 홀수줄의 배경색
 * @param {String} [options.evenRowBackgroundColor]	[defalut: 없음] excel파일에서 그리드 body의 짝수줄의 배경색
 * @param {String} [options.rowNumHeaderColor]		[defalut: 없음] rowNumVisible 속성이 true인 경우 순서출력 header 영역의 배경색
 * @param {String} [options.rowNumHeaderFontName]	[defalut: 없음] rowNumVisible 속성이 true인 경우 순서출력 header 영역의 폰트이름
 * @param {String} [options.rowNumHeaderFontSize]	[defalut: 없음] rowNumVisible 속성이 true인 경우 순서출력 header 영역의 폰트크기
 * @param {String} [options.rowNumHeaderFontColor]	[defalut: 없음] rowNumVisible 속성이 true인 경우 순서출력 header 영역의 폰트색상
 * @param {String} [options.rowNumBodyColor]			[defalut: 없음] rowNumVisible 속성이 true인 경우 순서출력 Body 영역의 배경색
 * @param {String} [options.rowNumBodyFontName]		[defalut: 없음] rowNumVisible 속성이 true인 경우 순서출력 Body 영역의 폰트이름
 * @param {String} [options.rowNumBodyFontSize]		[defalut: 없음] rowNumVisible 속성이 true인 경우 순서출력 Body 영역의 폰트크기
 * @param {String} [options.rowNumBodyFontColor]		[defalut: 없음] rowNumVisible 속성이 true인 경우 순서출력 Body 영역의 폰트색상
 * @param {String} [options.rowNumFooterColor]		[defalut: 없음] rowNumVisible 속성이 true인 경우 순서출력 Footer 영역의 배경색
 * @param {String} [options.rowNumFooterFontName]	[defalut: 없음] rowNumVisible 속성이 true인 경우 순서출력 Footer 영역의 폰트이름
 * @param {String} [options.rowNumFooterFontSize]	[defalut: 없음] rowNumVisible 속성이 true인 경우 순서출력 Footer 영역의 폰트크기
 * @param {String} [options.rowNumFooterFontColor]	[defalut: 없음] rowNumVisible 속성이 true인 경우 순서출력 Footer 영역의 폰트색상
 * @param {String} [options.rowNumSubTotalColor]		[defalut: 없음] rowNumVisible 속성이 true인 경우 순서출력 Subtotal 영역의 배경색
 * @param {String} [options.rowNumSubTotalFontName]	[defalut: 없음] rowNumVisible 속성이 true인 경우 순서출력 Subtotal 영역의 폰트이름
 * @param {String} [options.rowNumSubTotalFontSize]	[defalut: 없음] rowNumVisible 속성이 true인 경우 순서출력 Subtotal 영역의 폰트크기
 * @param {String} [options.rowNumSubTotalFontColo]	 [defalut: 없음] rowNumVisible 속성이 true인 경우 순서출력 Subtotal 영역의 폰트색상
 * @param {String} [options.rowNumHeaderValue]		[defalut: 없음] rowNumVisible 속성이 true인 경우 순서출력 Header 영역의 출력값
 * @param {String} [options.rowNumVisible]			[defalut: false] 순서출력 여부
 * @param {String} [options.showProcess]				[defalut: true] 다운로드 시 프로세스 창을 보여줄지 여부
 * @param {String} [options.massStorage]				[defalut: true] 대용량 다운로드 여부 <br>(default는 true 이 옵션을 true로 하고 showConfirm을 false로 한 경우에 IE에서 신뢰할만한 사이트를 체크하는 옵션이 뜬다.)
 * @param {String} [options.showConfirm]				[defalut: false] 다운로드 확인창을 띄울지 여부 <br>(옵션을 킨 경우 advancedExcelDownload를 호출후 사용자가 window의 버튼을 한번더 클릭해야 한다. massStorage는 자동으로 true가 된다)
 * @param {String} [options.dataProvider]			[defalut: 없음] 대량데이터 처리 및 사용자 데이터를 가공할 수 있는 Provider Package
 * @param {String} [options.providerRequestXml]		[defalut: 없음] Provider 내부에서 사용할 XML 문자열
 * @param {String} [options.userDataXml]				[defalut: 없음] 사용자가 서버모듈 개발 시 필요한 데이터를 전송 할 수 있는 변수
 * @param {String} [options.bodyWordwrap]			[defalut: false] 다운로드시 바디의 줄 바꿈 기능
 * @param {String} [options.useEuroLocal]			[defalut: false] 다운로드시 유로화 처리 기능(,와 .이 반대인 경우처리)
 * @param {String} [options.useHeader]				[defalut: true] 다운로드시 Header를 출력 할지 여부<br>  "true" => 출력 <br> "false" => 미출력
 * @param {String} [options.useSubTotal]				[defalut: false] 다운로드시 SubTotal을 출력 할지 여부<br>  "true" => 출력 <br> "false" => 미출력<br> expression을 지정한 경우 avg,sum,min,max,targetColValue,숫자를 지원 함.
 * @param {String} [options.useFooter]				[defalut: true] 다운로드시 Footer를 출력 할지 여부<br>  "true" => 출력<br> "false" => 미출력
 * @param {String} [options.separator]				[defalut: ,] 다운로드시 서버로 데이터 전송할때, 데이터를 구분짓는 구분자, default는 comma(,)
 * @param {Number} [options.subTotalScale]			[defalut: -1] 다운로드시 subTotal 평균계산시 소수점 자리수를 지정
 * @param {String} [options.subTotalRoundingMode]	[defalut: 없음] 다운로드시 subTotal 평균계산시 Round를 지정 한다. <br> "CEILING" => 소수점 올림 <br> "FLOOR" => 소수점 버림 <br>"HALF_UP" => 소수점 반올림
 * @param {String} [options.useStyle]				[defalut: false] 다운로드시 css를 제외한, style을 excel에도 적용할 지 여부 (배경색,폰트)
 * @param {String} [options.freezePane]				[defalut: ""] 틀고정을 위한 좌표값 및 좌표값의 오픈셋 <br>  freezePane="3,4" => X축 3, Y축 4에서 틀고정<br> freezePane="0,1,0,5" => X축 0, Y축 1에서 X축으로 0, Y축으로 5로 틀고정
 * @param {String} [options.autoSizeColumn]			[defalut: false] 너비자동맞춤 설정 여부 - 2021.08.18 옵션 설정을 true로 변경
 * @param {String} [options.displayGridlines]		[defalut: false] 엑셀 전체 셀의 눈금선 제거 여부
 * @param {String} [options.colMerge]				[defalut: false] colMerge된 컬럼을 Merge해서 출력 할 지 여부
 * @param {String} [options.useDataFormat]			[defalut: 없음] 그리드 dataType이 text인 경우, 엑셀의 표시형식 '텍스트' 출력 여부<br> "true" => 표시형식 텍스트<br> "false" => 표시형식 일반 출력)
 * @param {String} [options.indent]					[defalut: 0] 그리드 dataType이 drilldown인 경우, indent 표시를 위한 공백 삽입 개수
 * @param {String} [options.columnMove]				[defalut: false] 그리드 컬럼이동시 이동된 상태로 다운로드 여부 <br> "true" => 컬럼이동 순서대로 출력
 * @param {String} [options.columnOrder]				[defalut: 없음] 엑셀 다운로드시 다운로드되는 컬럼 순서를 지정 할 수 있는 속성 ( "0,3,2,1"로 지정시 지정한 순서로 다운로드된다 )
 * @param {String} [options.fitToPage]				[defalut: false] 엑셀 프린터 출력시 쪽맞춤 사용 여부
 * @param {String} [options.landScape]				[defalut: false] 엑셀 프린터 출력시 가로 방향 출력 여부
 * @param {String} [options.fitWidth]				[defalut: 1] 엑셀 프린터 출력시 용지너비
 * @param {String} [options.fitHeight]				[defalut: 1] 엑셀 프린터 출력시 용지높이
 * @param {String} [options.scale]					[defalut: 100] 엑셀 프린터 출력시 확대/축소 배율<br> scale을 사용할 경우 fitToPage는 false로 설정 해야 한다.
 * @param {String} [options.pageSize]				[defalut: A4] 엑셀 프린터 출력시 인쇄용지 설정 <br>  "A3", "A4", "A5", "B4"
 * @param {Object[]} [infoArr]						subTotalFontName 그리드에 대한 내용을 추가로 다른 셀에 표현하는 경우 사용하는 배열
 * @param {Number} [infoArr.rowIndex]				[defalut: ""] 내용을 표시할 행번호
 * @param {Number} [infoArr.colIndex]				[defalut: ""] 내용을 표시할 열번호
 * @param {Number} [infoArr.rowSpan]					[defalut: ""] 병합할 행의 수
 * @param {Number} [infoArr.colSpan]					[defalut: ""] 병합할 열의 수
 * @param {String} [infoArr.text]					[defalut: ""] 표시할 내용
 * @param {String} [infoArr.textAlign]				[defalut: "right"] 표시할 내용의 정렬 방법 <br> left => 좌측 정렬 <br> center => 가운데 정렬 <br> right => 우측 정렬
 * @param {String} [infoArr.fontSize]				[defalut: "10px"] font size 설정 20px, 10px, 5px
 * @param {String} [infoArr.fontName]				[defalut: ""] font name 설정
 * @param {String} [infoArr.color]					[defalut: ""] font color 설정 red, blue, green
 * @param {String} [infoArr.fontWeight]				[defalut: ""] font weight 설정 bold
 * @param {String} [infoArr.drawBorder]				[defalut: "true"] cell의 border지정 true, false
 * @param {String} [infoArr.wordWrap]				[defalut: ""] cell의 줄 바꿈 기능 true, false
 * @param {String} [infoArr.bgColor]					[defalut: ""] cell의 배경 color 설정 red, blue, green
 * @memberOf com
 * @return {file} <b>Excel file</b>
 * @author tracom
 * @example
 * var gridId = "grd_AdvancedExcel";
 * var infoArr = {};
 * var options = {
 *	 fileName : "downLoadExcel.xlsx" //[default : excel.xlsx] options.fileName 값이 없을 경우 default값 세팅
 * };
 * com.downLoadExcel(grdObj, options, infoArr );
*/
com.downLoadExcel = function(grdObj, options, infoArr) {

	if (typeof options === "undefined") {
		options = {
			hiddenVisible : false,
			fileName : "excel.xlsx"
		}
	}

	if (typeof infoArr === "undefined") {
		infoArr = {};
	}

	// excel 다운로드시 기본 설정으로 화면내의 hidden컬럼을 removeColumns에 포함시킨다.
	// 이를 원치 않을 경우 options.hiddenVisible = 'true' 로 설정한다.
	if (!options.hiddenVisible) {
		var grdCnt = grdObj.getTotalCol();

		var hiddenColIndex = [];
		for (var idx = 0; idx < grdCnt; idx++) {
			if (!grdObj.getColumnVisible(idx)) {
				hiddenColIndex.push(idx);
			}
		}
		// hidden 컬럼이 있는 경우만 추가할 수 있도록 (2021.10.28 추가)
		if (hiddenColIndex.length > 0) {
			if (options.removeColumns.length > 0) {
				options.removeColumns = options.removeColumns + "," + hiddenColIndex.join(',');
			} else {
				options.removeColumns = hiddenColIndex.join(',');
			}

			// 중복 요소 제거
			var _removeColumnArr = options.removeColumns.split(",");
			options.removeColumns = _removeColumnArr.reduce(function(a, b) {
				if (a.indexOf(b) < 0) {
					a.push(b);
				}
				return a;
			}, []).join(',');
		}
	}

	var options = {
		fileName : options.fileName || "excel.xlsx", // String, [defalut: excel.xlsx] 다운로드하려는 파일의 이름으로 필수 입력 값이다.
		sheetName : options.sheetName || "sheet", // String, [defalut: sheet] excel의 sheet의 이름
		type : options.type || "1", // String, [defalut: 0] type이 0인 경우 실제 데이터 1인 경우 눈에 보이는 데이터를 2이면 들어가 있는 data 그대로(filter무시 expression 타입의
		// 셀은 나오지 않음)
		removeColumns : options.removeColumns || "", // String, [defalut: 없음] 다운로드시 excel에서 삭제하려는 열의 번호(여러 개일 경우 ,로 구분)
		removeHeaderRows : options.removeHeaderRows || "", // String, [defalut: 없음] 다운로드시 excel에서 삭제하려는 Header의 row index(여러 개일 경우 ,로 구분)
		foldColumns : options.foldColumns || "", // String, [defalut: 없음] 다운로드시 excel에서 fold하려는 열의 번호(여러 개일 경우 ,로 구분)
		startRowIndex : options.startRowIndex || 0, // Number, excel파일에서 그리드의 데이터가 시작되는 행의 번호(헤더 포함)
		startColumnIndex : options.startColumnIndex || 0, // Number, excel파일에서 그리드의 데이터가 시작되는 열의 번호(헤더 포함)
		headerColor : options.headerColor || "#33CCCC", // String, excel파일에서 그리드의 header부분의 색
		headerFontName : options.headerFontName || "", // String, [defalut: 없음] excel파일에서 그리드의 header부분의 font name
		headerFontSize : options.headerFontSize || "10", // String, excel파일에서 그리드의 header부분의 font size
		headerFontColor : options.headerFontColor || "", // String, excel파일에서 그리드의 header부분의 font색
		bodyColor : options.bodyColor || "#FFFFFF", // String, excel파일에서 그리드의 body부분의 색
		bodyFontName : options.bodyFontName || "", // String, [defalut: 없음] excel파일에서 그리드의 body부분의 font name
		bodyFontSize : options.bodyFontSize || "10", // String, excel파일에서 그리드의 body부분의 font size
		bodyFontColor : options.bodyFontColor || "", // String, excel파일에서 그리드의 body부분의 font색
		subTotalColor : options.subTotalColor || "#CCFFCC", // String, [defalut: #CCFFCC] excel파일에서 그리드의 subtotal부분의 색
		subTotalFontName : options.subTotalFontName || "", // String, [defalut: 없음] excel파일에서 그리드의 subtotal부분의 font name
		subTotalFontSize : options.subTotalFontSize || "10", // String, [defalut: 10] excel파일에서 그리드의 subtotal부분의 font size
		subTotalFontColor : options.subTotalFontColor || "", // String, [defalut: 없음] excel파일에서 그리드의 subtotal부분의 font색
		footerColor : options.footerColor || "#008000", // String, [defalut: #008000] excel파일에서 그리드의 footer부분의 색
		footerFontName : options.footerFontName || "", // String, [defalut: 없음] excel파일에서 그리드의 footer부분의 font name
		footerFontSize : options.footerFontSize || "10", // String, [defalut: 10] excel파일에서 그리드의 footer부분의 font size
		footerFontColor : options.footerFontColor || "", // String, [defalut: 없음] excel파일에서 그리드의 footer부분의 font색
		oddRowBackgroundColor : options.oddRowBackgroundColor || "", // String, excel파일에서 그리드 body의 홀수줄의 배경색
		evenRowBackgroundColor : options.evenRowBackgroundColor || "", // String, [defalut: 없음] excel파일에서 그리드 body의 짝수줄의 배경색
		rowNumHeaderColor : options.rowNumHeaderColor || "", // String, [defalut: 없음] rowNumVisible 속성이 true인 경우 순서출력 header 영역의 배경색
		rowNumHeaderFontName : options.rowNumHeaderFontName || "", // String, [defalut: 없음] rowNumVisible 속성이 true인 경우 순서출력 header 영역의 폰트이름
		rowNumHeaderFontSize : options.rowNumHeaderFontSize || "", // String, [defalut: 없음] rowNumVisible 속성이 true인 경우 순서출력 header 영역의 폰트크기
		rowNumHeaderFontColor : options.rowNumHeaderFontColor || "", // String, [defalut: 없음] rowNumVisible 속성이 true인 경우 순서출력 header 영역의
		// 폰트색상
		rowNumBodyColor : options.rowNumBodyColor || "", // String, [defalut: 없음] rowNumVisible 속성이 true인 경우 순서출력 Body 영역의 배경색
		rowNumBodyFontName : options.rowNumBodyFontName || "", // String, [defalut: 없음] rowNumVisible 속성이 true인 경우 순서출력 Body 영역의 폰트이름
		rowNumBodyFontSize : options.rowNumBodyFontSize || "", // String, [defalut: 없음] rowNumVisible 속성이 true인 경우 순서출력 Body 영역의 폰트크기
		rowNumBodyFontColor : options.rowNumBodyFontColor || "", // String, [defalut: 없음] rowNumVisible 속성이 true인 경우 순서출력 Body 영역의 폰트색상
		rowNumFooterColor : options.rowNumFooterColor || "", // String, [defalut: 없음] rowNumVisible 속성이 true인 경우 순서출력 Footer 영역의 배경색
		rowNumFooterFontName : options.rowNumFooterFontName || "", // String, [defalut: 없음] rowNumVisible 속성이 true인 경우 순서출력 Footer 영역의 폰트이름
		rowNumFooterFontSize : options.rowNumFooterFontSize || "", // String, [defalut: 없음] rowNumVisible 속성이 true인 경우 순서출력 Footer 영역의 폰트크기
		rowNumFooterFontColor : options.rowNumFooterFontColor || "", // String, [defalut: 없음] rowNumVisible 속성이 true인 경우 순서출력 Footer 영역의
		// 폰트색상
		rowNumSubTotalColor : options.rowNumSubTotalColor || "", // String, [defalut: 없음] rowNumVisible 속성이 true인 경우 순서출력 Subtotal 영역의
		// 배경색
		rowNumSubTotalFontName : options.rowNumSubTotalFontName || "", // String, [defalut: 없음] rowNumVisible 속성이 true인 경우 순서출력 Subtotal
		// 영역의 폰트이름
		rowNumSubTotalFontSize : options.rowNumSubTotalFontSize || "", // String, [defalut: 없음] rowNumVisible 속성이 true인 경우 순서출력 Subtotal
		// 영역의 폰트크기
		rowNumSubTotalFontColor : options.rowNumSubTotalFontColor || "", // String, [defalut: 없음] rowNumVisible 속성이 true인 경우 순서출력
		// Subtotal 영역의 폰트색상
		rowNumHeaderValue : options.rowNumHeaderValue || "", // String, [defalut: 없음] rowNumVisible 속성이 true인 경우 순서출력 Header 영역의 출력값
		rowNumVisible : options.rowNumVisible || "false", // String, [defalut: false] 순서출력 여부
		showProcess : WebSquare.util.getBoolean(options.showProcess) || true, // Boolean, [defalut: true] 다운로드 시 프로세스 창을 보여줄지 여부
		massStorage : WebSquare.util.getBoolean(options.massStorage) || true, // Boolean, [defalut: true] 대용량 다운로드 여부 (default는 true 이 옵션을
		// true로 하고 showConfirm을 false로 한 경우에 IE에서 신뢰할만한 사이트를 체크하는
		// 옵션이 뜬다.)
		showConfirm : WebSquare.util.getBoolean(options.showConfirm) || false, // Boolean, [defalut: false] 다운로드 확인창을 띄울지 여부(옵션을 킨 경우
		// advancedExcelDownload를 호출후 사용자가 window의 버튼을 한번더 클릭해야 한다.
		// massStorage는 자동으로 true가 된다)
		dataProvider : options.dataProvider || "", // String, [defalut: 없음] 대량데이터 처리 및 사용자 데이터를 가공할 수 있는 Provider Package
		splitProvider : options.splitProvider || "", // String, [defalut: 없음] 대량데이터 처리 및 사용자 데이터를 가공할 수 있는 Split Provider Package
		providerRequestXml : options.providerRequestXml || "", // String, [defalut: 없음] Provider 내부에서 사용할 XML 문자열
		userDataXml : options.userDataXml || "", // String, [defalut: 없음] 사용자가 서버모듈 개발 시 필요한 데이터를 전송 할 수 있는 변수
		bodyWordwrap : WebSquare.util.getBoolean(options.bodyWordwrap) || false, // Boolean, [defalut: false] 다운로드시 바디의 줄 바꿈 기능
		useEuroLocale : options.useEuroLocale || "false", // String, [defalut: false] 다운로드시 유로화 처리 기능(,와 .이 반대인 경우처리)
		useHeader : options.useHeader || "true", // String, [defalut: true] 다운로드시 Header를 출력 할지 여부( "true"인경우 출력, "false"인경우 미출력)
		useSubTotal : options.useSubTotal || "false", // String, [defalut: false] 다운로드시 SubTotal을 출력 할지 여부( "true"인경우 출력, "false"인경우 미출력),
		// expression을 지정한 경우 avg,sum,min,max,targetColValue,숫자를 지원 함.
		useFooter : options.useFooter || "false", // String, [defalut: true] 다운로드시 Footer를 출력 할지 여부( "true"인경우 출력, "false"인경우 미출력)
		separator : options.separator || ",", // String, [defalut: ,] 다운로드시 서버로 데이터 전송할때, 데이터를 구분짓는 구분자, default는 comma(,)
		subTotalScale : options.subTotalScale || -1, // Number, [defalut: -1] 다운로드시 subTotal 평균계산시 소수점 자리수를 지정
		subTotalRoundingMode : options.subTotalRoundingMode || "", // String, [defalut: 없음] 다운로드시 subTotal 평균계산시 Round를 지정 한다.
		// ("CEILING","FLOOR","HALF_UP")
		useStyle : options.useStyle || "", // String, [defalut: false] 다운로드시 css를 제외한, style을 excel에도 적용할 지 여부 (배경색,폰트)
		freezePane : options.freezePane || "", // String, [defalut: ""] 틀고정을 위한 좌표값 및 좌표값의 오픈셋 ( ex) freezePane="3,4" X축 3, Y축 4에서 틀고정,
		// freezePane="0,1,0,5" X축 0, Y축 1에서 X축으로 0, Y축으로 5로 틀공정 )
		autoSizeColumn : options.autoSizeColumn || "true", // String, [defalut: false] 너비자동맞춤 설정 여부 - 2021.08.18 옵션 설정을 true로 변경
		displayGridlines : options.displayGridlines || "", // String, [defalut: false] 엑셀 전체 셀의 눈금선 제거 여부
		colMerge : options.colMerge || "", // String, [defalut: false] colMerge된 컬럼을 Merge해서 출력 할 지 여부
		useDataFormat : options.useDataFormat || "", // String, [defalut: 없음] 그리드 dataType이 text인 경우, 엑셀의 표시형식 '텍스트' 출력 여부( "true"인 경우
		// 표시형식 텍스트, "false"인 경우 표시형식 일반 출력)
		indent : options.indent || "", // String, [defalut: 없음] 그리드 dataType이 drilldown인 경우, indent 표시를 위한 공백 삽입 개수, default값은 0
		columnMove : options.columnMove || "", // String, [defalut: false] 그리드 컬럼이동시 이동된 상태로 다운로드 여부 ( "true"인경우 컬럼이동 순서대로 출력 )
		columnOrder : options.columnOrder || "", // String, [defalut: 없음] 엑셀 다운로드시 다운로드되는 컬럼 순서를 지정 할 수 있는 속성 ( ex) "0,3,2,1"로 지정시 지정한
		// 순서로 다운로드된다 )
		fitToPage : options.fitToPage || "false", // String, [defalut: false] 엑셀 프린터 출력시 쪽맞춤 사용 여부
		landScape : options.landScape || "false", // String, [defalut: false] 엑셀 프린터 출력시 가로 방향 출력 여부
		fitWidth : options.fitWidth || "1", // String, [defalut: 1] 엑셀 프린터 출력시 용지너비
		fitHeight : options.fitHeight || "1", // String, [defalut: 1] 엑셀 프린터 출력시 용지높이
		scale : options.scale || "100", // String, [defalut: 100] 엑셀 프린터 출력시 확대/축소 배율, scale을 사용할 경우 fitToPage는 false로 설정 해야 한다.
		pageSize : options.pageSize || "A4" // String, [defalut: A4] 엑셀 프린터 출력시 인쇄용지 설정 ( ex) "A3", "A4", "A5", "B4" )
	};

	var infoArr = {
		rowIndex : infoArr.rowIndex || 0, // Number, 내용을 표시할 행번호
		colIndex : infoArr.colIndex || 0, // Number, 내용을 표시할 열번호
		rowSpan : infoArr.rowSpan || 0, // Number, 병합할 행의 수
		colSpan : infoArr.colSpan || 0, // Number, 병합할 열의 수
		text : infoArr.text || "", // String, 표시할 내용
		textAlign : infoArr.textAlign || "right", // String, 표시할 내용의 정렬 방법 left, center, right
		fontSize : infoArr.fontSize || "10px", // String, font size 설정 20px, 10px, 5px
		fontName : infoArr.fontName || "", // String, font name 설정
		color : infoArr.color || "", // String, font color 설정 red, blue, green
		fontWeight : infoArr.fontWeight || "", // String, font weight 설정 bold
		drawBorder : infoArr.drawBorder || "true", // String, cell의 border지정 true, false
		wordWrap : infoArr.wordWrap || "", // String, cell의 줄 바꿈 기능 true, false
		bgColor : infoArr.bgColor || "" // String, cell의 배경 color 설정 red, blue, green
	};

	grdObj.advancedExcelDownload(options, infoArr);
};

/**
 * 설정된 옵션으로 CSV파일을 다운로드 한다.
 * 
 * @date 2021.11.30
 * @param {Object} grdObj GridView Object
 * @param {Object[]} options JSON형태로 저장된 그리드의 엑셀 다운로드 옵션
 * @param {String} options.fileName [default: excel.csv] 저장 될 파일 이름
 * @param {String} [options.type] [default: 1] Grid 저장 형태 (0이면 데이터 형태,1이면 표시 방식)
 * @param {String} [options.delim] [default: ,] CSV 파일에서 데이터를 구분할 구분자
 * @param {String} [options.removeColumns] [default: 없음] 저장 하지 않을 columns index, 여러컬럼인 경우 콤마(,)로 구분해서 정의 한다.
 * @param {String} [options.header] [default: 1] Grid의 숨겨진 Column에 대한 저장 여부(0이면 저장 하지 않음,1이면 저장)
 * @param {String} [options.hidden] [defalut: 0] Grid의 숨겨진 Column에 대한 저장 여부(0이면 저장 하지 않음,1이면 저장)
 * @param {String} [options.checkButton] [default: 1] Grid의 Control(Check, Radio, Button) Column에 대해 히든 여부 (0이면 control Column히든,1이면 보여줌)
 * @param {String} [options.saveList] [default: 없음] hidden에 관계없이 저장할 column id들의 array
 * @memberOf com
 * @return {file} CSV file
 * @author tracom
 * var gridId = "grd_AdvancedExcel";
 * var options = {
 *	 fileName : "downLoadCSV.csv" //[default : excel.csv] options.fileName 값이 없을 경우 default값 세팅
 * };
 * com.downLoadCSV(grdObj, options);
 * //return 예시) 엑셀 파일 다운로드
 */
com.downLoadCSV = function(grdObj, options) {
	var options = {
		fileName : options.fileName || "excel.csv", // [default: excel.csv] 저장 될 파일 이름
		type : options.type || "1", // [default: 1] Grid 저장 형태 (0이면 데이터 형태,1이면 표시 방식)
		delim : options.delim || ",", // [default: ,] CSV 파일에서 데이터를 구분할 구분자
		removeColumns : options.removeColumns || "", // [default: 없음] 저장 하지 않을 columns index, 여러컬럼인 경우 콤마(,)로 구분해서 정의 한다.
		header : options.header || "1", // [default: 1] Grid의 숨겨진 Column에 대한 저장 여부(0이면 저장 하지 않음,1이면 저장)
		hidden : options.hidden || 0, // [defalut: 0] Grid의 숨겨진 Column에 대한 저장 여부(0이면 저장 하지 않음,1이면 저장)
		checkButton : options.checkButton || "1", // [default: 1] Grid의 Control(Check, Radio, Button) Column에 대해 히든 여부 (0이면 control
		// Column히든,1이면 보여줌)
		saveList : options.saveList || "" // [default: 없음] hidden에 관계없이 저장할 column id들의 array
	}
	grdObj.saveCSV(options);
};

/**
 * 엑셀 업로드 옵션을 설정하고 확장자 별로 다른 함수(uploadCSV || uploadExcel)를 호출한다.
 * 
 * @date 2021.11.30
 * @param {Object} grdObj 그리드뷰 ID
 * @param {Array} options JSON형태로 저장된 그리드의 엑셀 업로드 옵션
 * @param {String} type 타입(xls, xlsx, csv)을 구분 후, 적합한 API를 사용하여 업로드 한다.
 * @memberOf com
 * @author tracom
 * @example
 * var gridId = "grd_AdvancedExcel";
 * var type = "xlsx";
 * var options = {
 *	 fileName : "gridDataUpload.xlsx" // default값이 존재하지 않으므로 꼭 fileName 값을 넣어야 한다.
 * };
 * com.gridDataUpload(grdObj, type, options);
 * //return 예시) com.js의 다른 함수(uploadCSV, uploadExcel)로 이동하거나 alert 발생
 */
com.gridDataUpload = function(grdObj, type, options) {
	type = type.toLowerCase();

	if (type == "csv") {
		com.uploadCSV(grdObj, options);
	} else if (type == "xls" || type == "xlsx") {
		com.uploadExcel(grdObj, options);
	} else {
		com.alert("지원하지 않는 파일 형식입니다.");
	}
};

/**
 * 엑셀 xls, xlsx 업로드
 *
 * @date 2021.11.30
 * @param {Object} grdObj GridView Object
 * @param {Object} options JSON형태로 저장된 그리드의 엑셀 업로드 옵션
 *
 * @param {String} [options.type]				1 => 엑셀 파일이 그리드의 보이는 결과로 만들어져있을때 <br> 0 => 엑셀 파일이 그리드의 실제 데이터로 구성되어있을때
 * @param {Number} [options.sheetNo]				excel파일에서 그리드의 데이터가 있는 sheet번호
 * @param {Number} [options.startRowIndex]		[defalut:0] excel파일에서 그리드의 데이터가 시작되는 행의 번호(헤더 포함)
 * @param {Number} [options.startColumnIndex]	[defalut:0] excel파일에서 그리드의 데이터가 시작되는 열의 번호
 * @param {Number} [options.endColumnIndex]		[defalut: 0] excel파일에서 그리드의 데이터가 끝나는 열의 index<br>
 *												(엑셀컬럼수가 그리드컬럼수 보다 작은 경우 그리드 컬러수를 설정)
 * @param {String} [options.headerExist]			[defalut:0] excel파일에서 그리드의 데이터에 header가 있는지 여부<br>
 *												1 => header 존재 <br> 0 => 없음)
 * @param {String} [options.footerExis]			[defalut: 1] excel파일에서 그리드의 데이터에 footer가 있는지 여부 <br>
 *												1 => footer 존재 <br> 0 => 없음 <br> (그리드에 footer가 없으면 적용되지 않음)
 * @param {String} [options.append]				[defalut: 0] excel파일에서 가져온 데이터를 그리드에 append시킬지 여부 <br>
 *												1 => 현재 그리드에 데이터를 추가로 넣어줌 <br> 0 => 현재 그리드의 데이터를 삭제하고 넣음)
 * @param {String} [options.hidden]				[defalut: 0] 읽어들이려는 엑셀파일에 hidden column이 저장되어 있는지 여부를 설정하는 int형 숫자<br>
 *												0 => 엑셀파일에 hidden 데이터가 없으므로 그리드 hidden column에 빈 데이터를 삽입 <br>
 *												 1 => 엑셀파일에 hidden 데이터가 있으므로 엑셀 파일로부터 hidden 데이터를 삽입
 * @param {String} [options.fillHidden]			[defalut: 0] Grid에 hiddenColumn에 빈 값을 넣을지를 결정하기 위한 int형 숫자<br>
 *												1 => hidden Column에 빈 값을 저장하지 않음 <br> 0 => hiddcolumn이 저장되어있지 않은 Excel  File이라 간주하고
 *												hidden Column에 값을 넣어줌 <br>(hidden이 0인 경우에는 fillhidden은 영향을 끼치지 않음)
 * @param {String} [options.skipSpace]			[defalut: 0] 공백무시 여부 <br> 1 => 무시 <br> 0 => 포함
 * @param {String} [options.insertColumns]		radio, checkbox와 같은 컬럼을 엑셀에서 받아 오지 않고 <br> 사용자 컬럼 설정 으로 업로드 ( 데이터 구조 : [ { columnIndex:1, columnValue:"1" } ] )
 * @param {String} [options.popupUrl]			업로드시에 호출할 popup의 url
 * @param {String} [options.status]				[defalut: R]업로드된 데이터의 초기 상태값, 설정하지 않으면 "R"로 설정되며 "C"값을 설정 할 수 있다.
 * @param {String} [options.pwd]				 엑셀파일에 암호가 걸려 있는 경우, 비밀번호
 * @memberOf com
 * @author tracom
 * @example
 * var options = {
 *	 type : "0"
 *	 ,sheetNo : 0
 * };
 * com.uploadExcel(grd_basicInfo,  options);
 */
com.uploadExcel = function(grdObj, options) {
	var options = {
		type : options.type || "0", // String, 1이면 엑셀 파일이 그리드의 보이는 결과로 만들어져있을때 0이면 엑셀 파일이 그리드의 실제 데이터로 구성되어있을때
		sheetNo : options.sheetNo || 0, // Number, excel파일에서 그리드의 데이터가 있는 sheet번호
		startRowIndex : options.startRowIndex || 1, // Number, [defalut:0] excel파일에서 그리드의 데이터가 시작되는 행의 번호(헤더 포함)
		startColumnIndex : options.startColumnIndex || 0, // Number, [defalut:0] excel파일에서 그리드의 데이터가 시작되는 열의 번호
		endColumnIndex : options.endColumnIndex || 0, // Number, [defalut: 0] excel파일에서 그리드의 데이터가 끝나는 열의 index
		// ( 엑셀컬럼수가 그리드컬럼수 보다 작은 경우 그리드 컬러수를 설정)
		headerExist : options.headerExist || "0", // String, [defalut:0] excel파일에서 그리드의 데이터에 header가 있는지 여부
		// (1이면 header 존재 0이면 없음)
		footerExist : options.footerExist || "0", // String, [defalut: 1] excel파일에서 그리드의 데이터에 footer가 있는지 여부
		// (1이면 footer 존재 0이면 없음 기본값은 1 그리드에 footer가 없으면 적용되지 않음)
		append : options.append || "0", // String, [defalut: 0] excel파일에서 가져온 데이터를 그리드에 append시킬지 여부
		// (1이면 현재 그리드에 데이터를 추가로 넣어줌 0이면 현재 그리드의 데이터를 삭제하고 넣음)
		hidden : options.hidden || "0", // String, [defalut: 0] 읽어들이려는 엑셀파일에 hidden column이 저장되어 있는지 여부를 설정하는 int형 숫자(0이면
		// 엑셀파일에 hidden 데이터가 없으므로 그리드 hidden column에 빈 데이터를 삽입
		// 1 : 엑셀파일에 hidden 데이터가 있으므로 엑셀 파일로부터 hidden 데이터를 삽입 )
		fillHidden : options.fillHidden || "0", // String, [defalut: 0] Grid에 hiddenColumn에 빈 값을 넣을지를 결정하기
		// 위한 int형 숫자(1이면 hidden Column에 빈 값을 저장하지 않음,0이면 hidden
		// column이 저장되어있지 않은 Excel File이라 간주하고 hidden Column에 빈
		// 값을 넣어줌)(hidden이 0인 경우에는 fillhidden은 영향을 끼치지 않음)
		skipSpace : options.skipSpace || "0", // String, [defalut: 0] 공백무시 여부(1이면 무시 0이면 포함)
		insertColumns : options.insertColumns || "",// Array, radio, checkbox와 같은 컬럼을 엑셀에서 받아 오지 않고
		// 사용자 컬럼 설정 으로 업로드 ( 데이터 구조 : [ { columnIndex:1, columnValue:"1" } ] )
		popupUrl : options.popupUrl || "", // String, 업로드시에 호출할 popup의 url
		status : options.status || "C", // String, [defalut: R]업로드된 데이터의 초기 상태값, 설정하지 않으면 "R"로 설정되며 "C"값을 설정 할 수 있다.
		pwd : options.pwd || "" // String, 엑셀파일에 암호가 걸려 있는 경우, 비밀번호
	};

	grdObj.advancedExcelUpload(options);

};

/**
 *  엑셀 CSV 업로드
 *
 * @date 2021.11.30
 * @param {Object} grdObj GridView Object
 * @param {Object} options JSON형태로 저장된 그리드의 엑셀 업로드 옵션
 * @param {String} [options.type] [default: 1, 0] 데이터 형태 (0이면 실 데이터 형태,1이면 display 표시 방식)
 * @param {String} [options.header] [default: 1, 0] Grid header 존재 여부 (0이면 header row수를 무시하고 전부 업로드하고 1이면 header row수 만큼 skip한다.)
 * @param {String} [options.delim] [default: ','] CSV 파일에서 데이터를 구분할 구분자
 * @param {String} [options.escapeChar] CSV 데이터에서 제거해야 되는 문자셋 ( ex) '\'' )
 * @param {Number} [options.startRowIndex] [defalut: 0] csv파일에서 그리드의 데이터가 시작되는 행의 번호, startRowIndex가 설정되면, header 설정은 무시된다.
 * @param {String} [options.append] [defalut: 0, 1] csv파일에서 가져온 데이터를 그리드에 append시킬지 여부(1이면 현재 그리드에 데이터를 추가로 넣어줌 0이면 현재 그리드의 데이터를 삭제하고 넣음)
 * @param {Number} [options.hidden] [defalut: 0, 1] hidden Column에 대한 저장 여부(0이면 저장하지않음,1이면 저장)
 * @param {String} [options.fillHidden] [defalut: 0, 1] hidden Column에 빈 값을 넣을지를 결정하기 위한 int형 숫자(1이면 hidden Column에 빈 값을 저장하지 않음,0이면 hidden column이 저장되어있지 않은 csv File이라 간주하고 hidden Column에 빈 값을 넣어줌)(hidden이 0인 경우에는 fillhidden은 영향을 끼치지 않음)
 * @param {String} [options.skipSpace] [defalut: 0, 1] 공백무시 여부(1이면 무시 0이면 포함)
 * @param {String} [options.expression] [defalut: 1, 0] expression 컬럼 데이터를 포함하고 있는지 여부, 기본값은 미포함(1이면 미포함, 0이면 포함)
 * @param {String} [options.popupUrl] 업로드시에 호출할 popup의 url
 * @memberOf com
 * @author tracom
 * @example
 * var gridId = "grd_AdvancedExcel";
 * var options = {};
 * com.uploadCSV( gridId,  options);
 * //return 예시) 엑셀 파일(.CSV) 업로드
 */
com.uploadCSV = function(grdObj, options) {
	var options = {
		type : options.type || "0", // String, [default: 1, 0]데이터 형태 (0이면 실 데이터 형태,1이면 display 표시 방식)
		header : options.header || "0", // String, [default: 1, 0]Grid header 존재 여부 (0이면 header row수를 무시하고 전부 업로드하고 1이면 header row수 만큼
		// skip한다.)
		delim : options.delim || ",", // String, [default: ',']CSV 파일에서 데이터를 구분할 구분자
		escapeChar : options.escapeChar || "", // String, CSV 데이터에서 제거해야 되는 문자셋 ( ex) '\'' )
		startRowIndex : options.startRowIndex || 0, // Number, [defalut: 0] csv파일에서 그리드의 데이터가 시작되는 행의 번호, startRowIndex가 설정되면, header 설정은
		// 무시된다.
		append : options.append || "0", // String, [defalut: 0, 1]csv파일에서 가져온 데이터를 그리드에 append시킬지 여부(1이면 현재 그리드에 데이터를 추가로 넣어줌 0이면 현재 그리드의
		// 데이터를 삭제하고 넣음)
		hidden : options.hidden || 1, // Number, [defalut: 0, 1]hidden Column에 대한 저장 여부(0이면 저장하지않음,1이면 저장)
		fillHidden : options.fillHidden || "0", // String, [defalut: 0, 1]hidden Column에 빈 값을 넣을지를 결정하기 위한 int형 숫자(1이면 hidden Column에 빈 값을
		// 저장하지 않음,0이면 hidden column이 저장되어있지 않은 csv File이라 간주하고 hidden Column에 빈 값을 넣어줌)(hidden이 0인
		// 경우에는 fillhidden은 영향을 끼치지 않음)
		skipSpace : options.skipSpace || "0", // String, [defalut: 0, 1]공백무시 여부(1이면 무시 0이면 포함)
		expression : options.expression || "1", // String, [defalut: 1, 0]expression 컬럼 데이터를 포함하고 있는지 여부, 기본값은 미포함(1이면 미포함, 0이면 포함)
		popupUrl : options.popupUrl || "" // String, 업로드시에 호출할 popup의 url
	}
	grdObj.readCSV(options);
};

/**
 * 해당 그룹 안의 컴포넌트에서 엔터키가 발생하면 해당 컴포넌트의 값을 DataColletion에 저장하고 objFunc 함수를 실행한다.
 *
 * @date 2021.02.15
 * @param {Object} grpObj 그룹 객체
 * @param {Object} objFunc 함수 객체
 * @param {Number} rowIndex DataList가 바인딩된 gridView인 경우 ==> 현재 포커스된 focusedRowIndex [ex. gridViewId.getFocusedRowIndex()]
 *				 <br/>아닌 경우 ==> rowIndex는 생략
 * @memberOf com
 * @author tracom
 * @example
 * com.setEnterKeyEvent(grp_AuthorityDetail, scwin.search);
 * // return 예시) "엔터키가 발생 -> 해당 함수 실행 및 DataColletion에 UI 컴포넌트에 입력된 데이터를 DataCollection에 저장"
 */
com.setEnterKeyEvent = function(grpObj, objFunc) {
	var objArr = WebSquare.util.getChildren(grpObj, {
		excludePlugin : "group trigger textbox output calendar image span",
		recursive : true
	});

	try {
		for (var i = 0; i < objArr.length; i++) {
			try {
				if (typeof objFunc === "function") {
					objArr[i].bind("onkeyup", function(e) {
						if (e.keyCode === 13) {
							if (typeof this.getRef === "function") {
								var ref = this.getRef();
								var refArray = ref.substring(5).split(".");
								if ((typeof refArray !== "undefined") && (refArray.length === 2)) {
									var dataCollectionName = refArray[0];
									var columnId = refArray[1];
									var dataCollection = this.getScopeWindow().$p.getComponentById(dataCollectionName);
									var dataType = dataCollection.getObjectType().toLowerCase();
									if (dataType === "datamap") {
										dataCollection.set(columnId, this.getValue());
									} else if ((dataType === 'datalist') && (typeof rowIndex !== "undefined")) {
										dataCollection.setCellData(dataCollection.getRowPosition(), columnId, this.getValue());
									}
								}
								objFunc();
							}
						}
					});
				}
			} catch (e) {
				$p.log("[com.setEnterKeyEvent] Exception :: " + e.message);
			} finally {
				dataCollection = null;
			}
		}
	} catch (e) {
		$p.log("[com.setEnterKeyEvent] Exception :: " + e.message);
	} finally {
		objArr = null;
	}
};

/**
 * Alert 메시지 창을 호출한다.
 *
 * @date 2021.12.30
 * @memberOf com
 * @param {String} messageStr 메시지
 * @param {String} closeCallbackFncName 콜백 함수명
 * @author Inswave Systems
 * @example
 * com.alert("우편번호를 선택하시기 바랍니다.");
 * com.alert("우편번호를 선택하시기 바랍니다.", "scwin.alertCallBack");
 */
com.alert = function(messageStr, closeCallbackFncName) {
	
	com.messagBox("alert", messageStr, closeCallbackFncName);
};

/**
 * 메세지 팝업을 호출한다.
 *
 * @date 2021.12.30
 * @param {String} messageType 팝업창 타입 (alert || confirm)
 * @param {String} messageStr 메시지
 * @param {String} closeCallbackFncName 콜백 함수명
 * @param {Boolean} isReturnValue Confirm 창인 경우 선택 결과(boolean)을 반환할지 여부
 * @param {String} title 팝업창 타이틀
 * @memberOf com
 * @author Inswave Systems
 * @example
 * //alert창을 띄울 경우
 * scwin.callback = function(){
 *	 console.log("콜백 함수입니다.");
 * };
 * com.messagBox("alert", "보낼 메시지", "callback", false, "팝업 타이틀");
 *
 * //confirm창을 띄울 경우
 * scwin.callback = function(){
 *	 console.log("콜백 함수입니다.");
 * };
 * com.messagBox("confirm", "보낼 메시지", "callback", true, "팝업 타이틀"); //isReturnValue속성에 false 사용가능
 */
com.messagBox = function(messageType, messageStr, closeCallbackFncName, isReturnValue, title) {
	var messageStr = messageStr || "";
	var messageType = messageType || "alert";
	var defaultTitle = null;
	var popId = messageType || "Tmp";
	
	popId = popId + com.MESSAGE_BOX_SEQ++;

	if (messageType === "alert") {
		defaultTitle = "Alert";
	} else {
		defaultTitle = "Confirm";
	}

	if (typeof isReturnValue === "undefined") {
		isReturnValue = false;
	}

	// closeCallBackFnc 정보관리
	if (typeof closeCallbackFncName == "function") {
		var cbFuncIdx = ++gcm.CB_FUNCTION_MANAGER["cbFuncIdx"];
		var idx = "__close_callback_Func__" + new Date().getTime() + "_" + cbFuncIdx;
		gcm.CB_FUNCTION_MANAGER["cbFuncSave"][$p.id + idx] = closeCallbackFncName;
		closeCallbackFncName = idx;
	}

	var data = {
		"message" : messageStr,
		"callbackFn" : closeCallbackFncName,
		"isReturnValue" : isReturnValue,
		"messageType" : defaultTitle
	};
	var options = {
		id : popId,
		popupName : defaultTitle,
		title : title || defaultTitle,
		width : 340,
		height : 150
	};
	com.openPopup(gcm.CONTEXT_PATH + "/cm/common/message_box.xml", options, data);
};

/**
 * Confirm 메시지 창을 호출한다.
 * 
 * @date 2021.10.09
 * @memberOf com
 * @param {String} messageStr 메시지
 * @param {String} closeCallbackFncName 콜백 함수명
 * @author Inswave Systems
 * @example
 * com.confirm("변경된 코드 그룹 정보를 저장하시겠습니까?", "scwin.saveCodeCoConfirmCallback");
 * com.confirm("하위에 새로운 조직을 추가하시겠습니까?", "scwin.insertConfirmCallBack");
 */
com.confirm = function(messageStr, closeCallbackFncName) {
	com.messagBox("confirm", messageStr, closeCallbackFncName);
};


/*com.alert = function(messageStr, closeCallbackFncName) {
	com.messagBox("alert", messageStr, closeCallbackFncName);
};*/


/**
 * 팝업창을 닫는다. callbackStr을 이용하여 부모창의 callback함수를 호출한다.
 * 
 * @date 2021.10.09
 * @memberOf com
 * @param {String} popId popup창 id로 값이 없을 경우 현재창의 ID(this.popupID) close.
 * @param {String} [callbackStr] callbackFunction명으로 부모 객체는 opener || parent으로 참조한다. opener || parent가 없을 경우 window 참조.
 * @param {String} [returnValue] callbackFunction에 넘겨줄 파라메터로 String타입을 권장한다.
 * @author Inswave Systems
 * @example
 * com.closePopup();
 * com.closePopup("scwin.zipPopupCallback" , '{message:"정상처리되었습니다"}');
 * com.closePopup("scwin.zipPopupCallback" , '정상처리되었습니다.');
 */
com.closePopup = function(callbackFnStr, retObj, callbackYn, selectedIdx) {
	com._closePopup($p.getPopupId(), callbackFnStr, com.strSerialize(retObj), window); // IFrame일 경우, 메모리릭을 없애기 위한 코딩. (부모/자식 간 페이지로 객체
	// 파라미터 전달 방식은 비권장. 문자열 전달 권장.)
};

com._closePopup = function(popId, callbackFnStr, retStr, winObj) {
	if ((typeof callbackFnStr !== "undefined") && (callbackFnStr !== "")) {
		var func;
		if (callbackFnStr.indexOf("__close_callback_Func__") > -1) {
			func = gcm.CB_FUNCTION_MANAGER["cbFuncSave"][callbackFnStr];
			delete gcm.CB_FUNCTION_MANAGER["cbFuncSave"][callbackFnStr];
		} else {
			func = winObj.WebSquare.util.getGlobalFunction(callbackFnStr);
		}

		if (func) {
			$p.closePopup(popId);
			func(com.getJSON(retStr));
		} else {
			var parentObj = opener || parent;
			if (winObj.$p.getParameter("w2xPath") !== parentObj.$p.getParameter("w2xPath")) {
				com._closePopup(popId, callbackFnStr, retStr, parentObj);
				return;
			}
			$p.closePopup(popId);
		}
	} else {
		$p.closePopup(popId);
	}
};

/**
 * 팝업창을 연다.
 * 
 * @date 2021.10.09
 * @param {String} url url 화면경로
 * @param {Array} options Popup창 옵션
 * @param {String} [options.id] Popup창 ID
 * @param {String} [options.type] 화면 오픈 타입 ("iframePopup", "wframePopup", "browserPopup")
 * @param {String} [options.width] Popup창 넓이
 * @param {String} [options.height] Popup창 높이
 * @param {String} [options.popupName] useIframe : true시 popup 객체의 이름으로 popup 프레임의 표시줄에 나타납니다.
 * @param {String} [options.useIFrame] [default : false] true : IFrame 을 사용하는 WebSquare popup / false: window.open 을 사용하는 popup
 * @param {String} [options.style] Popup의 스타일을 지정합니다. 값이 있으면 left top width height는 적용되지 않습니다.
 * @param {String} [options.resizable] [default : false]
 * @param {String} [options.modal] [default : false]
 * @param {String} [options.scrollbars] [default : false]
 * @param {String} [options.title] [default : false]
 * @param {String} [options.notMinSize] [default : false]
 * @memberOf com
 * @author Inswave Systems
 * @example
 * var data = { data : dma_authority.getJSON(), callbackFn : "scwin.insertMember" };
 * var options = { id : "AuthorityMemberPop", 
 *				 popupName : "직원 조회", 
 *				 modal : true, 
 *				 width : 560, height: 400 };
 * com.openPopup("/ui/BM/BM002P01.xml", options, data); 
 */
com.openPopup = function(url, opt, data) {
	com._openPopup(url, opt, data);
};

com._openPopup = function(url, opt, data) {
	
	if((typeof opt.width == "undefined")||(opt.width==null)||(opt.width==0))opt.width=800;
	if((typeof opt.height == "undefined")||(opt.height==null)||(opt.width==0))opt.height=600;
	
	var width = opt.width;
	var height = opt.height ;
	
	data.width=opt.width;
	data.height=opt.height;
	var _dataObj = {
		type : "json",
		data : data,
		name : "param"
	};
	
	try {
		var deviceWidth = parseFloat($("body").css("width"));
		var deviceHeight = parseFloat($("body").css("height"));
		if (!opt.notMinSize) {
			if (deviceWidth > 0 && width > deviceWidth) {
				width = deviceWidth - 4; // 팝업 border 고려
			}

			if (deviceHeight > 0 && height > deviceHeight) {
				height = deviceHeight - 4; // 팝업 border 고려
			}
		}
	} catch (e) {

	}

	var top = ((document.body.offsetHeight / 2) - (parseInt(height) / 2) + $(document).scrollTop()) + "px";
	var left = ((document.body.offsetWidth / 2) - (parseInt(width) / 2) + $(document).scrollLeft()) + "px";

	if (typeof _dataObj.data.callbackFn === "undefined") {
		_dataObj.data.callbackFn = "";
	} else if (_dataObj.data.callbackFn.indexOf("gcm") !== 0) {
		_dataObj.data.callbackFn = $p.id + _dataObj.data.callbackFn;
	}

	var options = {
		id : opt.id,
		popupName : opt.popupName || "",
		type : opt.type || "wframePopup",
		width : width + "px",
		height : height + "px",
		top : opt.top || top || "140px",
		left : opt.left || left || "500px",
		modal : (opt.modal == false) ? false : true,
		dataObject : _dataObj,
		alwaysOnTop : opt.alwaysOnTop || false,
		useModalStack : (opt.useModalStack == false) ? false : true,
		resizable : (opt.resizable == false) ? false : true,
		useMaximize : opt.useMaximize || false
	};

	$p.openPopup(url, options);
};

/**
 * 그룹안에 포함된 컴포넌트의 입력 값에 대한 유효성을 검사한다.
 * 컴포넌트 속성 유효성 검사를 수행하고, valInfoArr 유효성 검사 옵션에 대해서 유효성 검사를 수행한다.
 * valInfoArr 유효성 검사 옵션 파라미터를 전달하지 않은 경우 컴포넌트 속성(mandatory, allowChar, ignoreChar, maxLength, maxByteLength, minLength, minByteLength)에 대해서만 유효성 검사를 수행한다.
 * 
 * @date 2021.01.19
 * @memberOf com
 * @param {Object} grpObj 그룹 컴포넌트 객체
 * @param {Object[]} options 유효성 검사 옵션 <br/>
 * @param {String} options[].id : 유효성 검사 대상 DataCollection 컬럼 ID <br/> 
 * @param {Boolean} options[].mandatory : 필수 입력 값 여부 <br/>
 * @param {Number} options[].minLength : 최소 입력 자리수 <br/>
 * @param {requestCallback} options[].valFunc : 사용자 유효성 검사 함수 <br/>
 * @param {String} tacId 그룹이 포함된 TabControl 컴포넌트 ID
 * @param {String} tabId 그룹이 포함된 TabControl 컴포넌트의 Tab ID
 * @returns {Boolean} 유효성 검사 결과
 * @since 2015.08.05
 * @example
 * 
 * if (com.validateGroup(grp_LoginInfo)) {
 *	 if (confirm("변경된 데이터를 저장하시겠습니까?")) {
 *		 com.executeSubmission("WS0201U04");
 *	 }
 * }
 * 
 * var valInfo = [ { id : "grpCd", mandatory : true, minLength : 5 }, 
 *				 { id : "grpNm", mandatory : true } ];
 * 
 * if (com.validateGroup(grp_LoginInfo, valInfo)) {
 *	 if (confirm("변경된 데이터를 저장하시겠습니까?")) {
 *		 com.executeSubmission("WS0201U04");
 *	 }
 * }
 * 
 * var valInfo = [ { id : "totWeight", mandatory : true }, 
 *				 { id : "totWeightPwr", mandatory : true }, 
 *				 { id : "totWeightPwr", mandatory : true },
 *				 { id : "ibxWeight1", mandatory : true, 
 *					 valFunc : function() {
 *						 if (numLib.parseInt(ibxTotWeight.getValue()) < numLib.parseInt(ibxWeight1.getValue())) {
 *							 return "총 중량이 세부 중량보다 커야 합니다.";
 *						 }
 *					 } }, 
 *				 { id : "winding", mandatory : true } ];
 * 
 * if (com.validateGroup(grpCsInfo, valInfo, tacCsInfo, "tabCsInfo1") == false) {
 *	 return false;
 * }
 * 
 * var valInfo = [ { id : "prntMenuCd", mandatory : true }, 
 *				 { id : "menuCd", mandatory : true, 
 *					 valFunc : function() {
 *						 if (dmaMenu.get("prntMenuCd") == dmaMenu.get("menuCd")) {
 *							 return "상위 메뉴 코드와 메뉴 코드가 같아서는 안됩니다.";
 *						 }
 *					 } }, 
 *				  { id : "menuNm", mandatory : true }, 
 *				  { id : "menuLevel", mandatory : true }, 
 *				  { id : "menuSeq", mandatory : true }, 
 *				  { id : "urlPath", mandatory : true },
 *				  { id : "isUse", mandatory : true } ];
 * 
 * if (com.validateGroup(tblMenuInfo, valInfo, tacMenuInfo, "tabMenuInfo1") == false) {
 *	 return false;
 * }
 * 
 * @description
 * ※ 입력 허용 문자, 입력 허용 불가 문자, 최대 입력 문자수 설정은 컴포넌트의 속성에서 설정을 권한한다. <br/>
 * - allowChar : 입력 허용 문자 <br/>
 * - ignoreChar : 입력 허용 불가 문자 <br/>
 * - maxLength : 최대 입력 문자수 <br/>
 * - maxByteLength : 최대 입력 바이트수 <br/>
 */
com.validateGroup = function(grpObj, valInfoArr, tacObj, tabId) {
	var objArr = WebSquare.util.getChildren(grpObj, {
		excludePlugin : "group trigger textbox output calendar image span anchor pageInherit wframe itemTable",
		recursive : true
	});

	var valStatus = {
		isValid : true,
		message : "",
		error : []
	// { columnId: "", comObjId: "", columnNam : "", message: "" }
	};
	
	try {
		for ( var objIdx in objArr) {
			var obj = objArr[objIdx];

			if ((typeof objArr[objIdx].validate === "function") && (objArr[objIdx].validate() === false)) {
				return false;
			}

			var dataObjInfo = com.getDataCollection(obj);
			var dataCollection = null;
			var columnId = null;
			var value = null;

			if ((dataObjInfo !== undefined) && (dataObjInfo !== null)) {
				dataCollection = WebSquare.util.getComponentById(dataObjInfo.runtimeDataCollectionId);
				columnId = dataObjInfo.columnId;
			}
			
			if ((dataCollection !== null) && (dataCollection.getObjectType() === "dataMap")) {
				value = dataCollection.get(dataObjInfo.columnId).trim();
			} else {
				var tempIdArr = obj.getID().split("_");
				if (obj.getPluginName() !== "editor") {
					if ( (typeof obj.formatter !== "undefined")&&(obj.formatter.type === "number") ) {
						if ((obj.realValue === null) || obj.realValue === "" || obj.realValue === " " ) {
							obj.realValue = 0;
							obj.setValue(obj.realValue);
						}
					}
					
					
					if ((obj.getValue != null) &&(typeof obj.getValue === "function") && (typeof obj.getValue().trim === "function")) {
						value = obj.getValue().trim();
					} else {
						continue;
					}
				} else {
					value = obj.getText().trim();
				}
			}

			try {
				
				var userData1 = obj.getUserData("userData1");	
				if((value.length === 0) && (typeof userData1 !== "undefined") && (userData1.length !== 0)){
					if (userData1 === 'M') {
						setResult(dataCollection, columnId, obj.getID(), "필수 입력 항목 입니다.");
					}
				}
				
				var userData2 = obj.getUserData("userData2");	
				if((typeof userData2 !== "undefined") && (userData2.length !== 0)){
					var func = eval(userData2);
					if(func(value)==false){

						var resultMsg = "유효하지 않은 형식입니다.";
					
						if ((typeof resultMsg !== "undefined") && (resultMsg !== "")) {
							_setResult(dataCollection, columnId, obj.getID(), resultMsg);
						}
					}
				}
			} catch (e) {
				console.log("[com.validateGroup] Exception :: Object Id : " + obj.getID() + ", Plug-in Name: " + obj.getPluginName() + ", "
						+ e.message);
			}
		}

		if (valStatus.error.length > 0) {
			valStatus.isValid = false;
			valStatus.message = "유효하지 않은 값이 입력 되었습니다";

			if ((typeof tacObj !== "undefined") && (typeof tabId !== "undefined") && (tabId !== "")) {
				var tabIndex = tacObj.getTabIndex(tabId);
				tacObj.setSelectedTabIndex(tabIndex);
			}

			gcm.valStatus.objectType = "group";
			gcm.valStatus.isValid = false;
			gcm.valStatus.objectName = valStatus.error[0].comObjId;

			com.alert(valStatus.error[0].message, "gcm._groupValidationCallback");
		}

		return valStatus.isValid;

		function _setResult(dataCollection, columnId, comObjId, message) {
			var scope = gcm._getScope(dataCollection);

			var errIdx = valStatus.error.length;
			valStatus.error[errIdx] = {};
			valStatus.error[errIdx].columnId = columnId;
			valStatus.error[errIdx].comObjId = comObjId;

			if (dataCollection !== null) {
				var comObj = scope.$p.getComponentById(comObjId);
				valStatus.error[errIdx].columnName = scope.com.getColumnName(comObj);
			} else {
				valStatus.error[errIdx].columnName = comObj.getInvalidMessage();
			}
			valStatus.error[errIdx].message = scope.com.attachPostposition(valStatus.error[errIdx].columnName) + " " + message;
		}
	} catch (e) {
		console.log("[com.validateGroup] Exception :: Object Id : " + obj.getID() + ", Plug-in Name: " + obj.getPluginName() + ", "
				+ e.message);
	} finally {
		objArr = null;
	}
};

/**
 * GridView를 통해서 입력된 데이터에 대해서 유효성을 검증한다.
 * 
 * @date 2021.01.19
 * @memberOf com
 * @param {Object} gridViewObj GridView 객체
 * @param {Object[]} options 데이터 유효성 검증 옵션
 * @param {String} options[].id 유효성 검사 대상 DataCollection 컬럼 ID
 * @param {Boolean} options[].mandatory 필수 입력 값 여부
 * @param {Number} options[].minLength 최소 입력 자리수
 * @param {requestCallback} options[].valFunc 사용자 유효성 검사 함수
 * @param {Object} tacObj GridView가 포함된 TabControl 컴포넌트 객체
 * @param {String} tabId GridView가 포함된 TabControl 컴포넌트의 Tab ID
 * @returns {Boolean} 유효성검사 결과
 * @since 2015.08.05
 * @example 
 * var valInfo = [ {id: "grpCd", mandatory: true, minLength: 5}, 
 *				 {id: "grpNm", mandatory: true} ];
 * 
 * if (com.validateGridView(grd_MenuAuthority, valInfo)) {
 *	 if (confirm("변경된 데이터를 저장하시겠습니까?")) {
 *		 scwin.saveGroup();
 *	 }
 * }
 * 
 * var valInfo = [ { id : "prntMenuCd", mandatory : true }, 
 *				 { id : "menuCd", mandatory : true, 
 *					 valFunc : function() {
 *						 if (dmaMenu.get("prntMenuCd") == dmaMenu.get("menuCd")) {
 *							 return "상위 메뉴 코드와 메뉴 코드가 같아서는 안됩니다.";
 *						 }
 *					 } },
 *				  { id : "menuNm", mandatory : true }, 
 *				  { id : "menuLevel", mandatory : true }, 
 *				  { id : "menuSeq", mandatory : true }, 
 *				  { id : "urlPath", mandatory : true },
 *				  { id : "isUse", mandatory : true } ];
 * 
 * if (com.validateGridView(grd_MenuAuthority, valInfo, tacMenuInfo, "tabMenuInfo1") == false) {
 *	 return false;
 * }
 * @description 
 * * 입력 허용 문자, 입력 허용 불가 문자, 최대 입력 문자수 설정은 GridView의 Column의 속성에서 설정한다. <br/>
 * - allowChar : 입력 허용 문자 <br/>
 * - ignoreChar : 입력 허용 불가 문자 <br/>
 * - maxLength : 최대 입력 문자수 <br/>
 * - maxByteLength : 최대 입력 바이트수 <br/>
 */
com.validateGridView = function(gridViewObj, tacObj, tabId) {

	if (gridViewObj === null) {
		return false;
	}

	var dataList = com.getGridViewDataList(gridViewObj);
	if (dataList === null) {
		$p.log("Can not find the datalist of '" + gridViewObjId + "' object.");
		return false;
	}

	var valStatus = {
		isValid : true,
		message : "",
		error : []
	// { columnId: "", columnName: "", rowIndex: 0, message: "" }
	};

	try {
		var modifiedIdx = dataList.getModifiedIndex();

		for (var dataIdx = 0; dataIdx < modifiedIdx.length; dataIdx++) {
			var index = modifiedIdx[dataIdx];
			var modifiedData = dataList.getRowJSON(index);
			if (modifiedData.rowStatus === "D") {
				continue;
			}
			/*for ( var valIdx in valInfoArr) {
				var valInfo = valInfoArr[valIdx];
				if ((typeof valInfo.id !== "undefined") && (typeof modifiedData[valInfo.id] !== "undefined")) {
					var value = modifiedData[valInfo.id].trim();
					if ((typeof valInfo.mandatory !== "undefined") && (valInfo.mandatory === true) && (value.length === 0)) {
						_setResult(modifiedIdx[dataIdx], dataList, gridViewObj.getID(), valInfo.id, "필수 입력 항목 입니다.");
					} else if ((typeof valInfo.minLength !== "undefined") && (valInfo.minLength > 0) && (value.length < valInfo.minLength)) {
						_setResult(modifiedIdx[dataIdx], dataList, gridViewObj.getID(), valInfo.id, "최소 길이 " + valInfo.minLength
								+ "자리 이상으로 입력해야 합니다.");
					} else if (typeof valInfo.valFunc === "function") {
						var resultMsg = valInfo.valFunc(value, modifiedData);
						if ((typeof resultMsg !== "undefined") && (resultMsg !== "")) {
							_setResult(modifiedIdx[dataIdx], dataList, gridViewObj.getID(), valInfo.id, resultMsg);
						}
					}
				}

				if (valStatus.error.length > 0) {
					break;
				}
			}*/
			for ( var columnIdx =0; columnIdx < gridViewObj.getColumnCount(); columnIdx++) {

				try {
					var columnId = gridViewObj.getColumnID(columnIdx);
					
					if((typeof modifiedData[columnId] == "undefined")
							||(modifiedData[columnId]==null))continue;
					
					var value = modifiedData[columnId].trim();
					var userData1 = gridViewObj.getCellOption(index,columnIdx,"userData1");			
					if((value.length === 0) && (typeof userData1 !== "undefined") && (userData1.length !== 0)){
						if (userData1 === 'M') {
							_setResult(index, dataList, gridViewObj.getID(), columnId, "필수 입력 항목 입니다.");
						}
						if (valStatus.error.length > 0) {
							break;
						}
					}
					
					var userData2 = gridViewObj.getCellOption(index,columnIdx,"userData2");	
					if((typeof userData2 !== "undefined") && (userData2.length !== 0)){
						var func = eval(userData2);
						if(func(value)==false){
	
							var resultMsg = "유효하지 않은 형식입니다.";
						
							if ((typeof resultMsg !== "undefined") && (resultMsg !== "")) {
								_setResult(index, dataList, gridViewObj.getID(), columnId, resultMsg);
							}
							if (valStatus.error.length > 0) {
								break;
							}
						}
					}
				} catch (e) {
					$p.log("[com.validateGridTableView] Exception :: " + e.message);
				}		
			}
		}

		if (valStatus.error.length > 0) {
			valStatus.isValid = false;
			valStatus.message = "유효하지 않은 값이 입력 되었습니다";

			if ((typeof tacObj !== "undefined") && (typeof tabId !== "undefined") && (tabId !== "")) {
				var tabIndex = tacObj.getTabIndex(tabId);
				tacObj.setSelectedTabIndex(tabIndex);
			}

			gcm.valStatus.isValid = false;
			gcm.valStatus.objectType = "gridView";
			gcm.valStatus.objectName = valStatus.error[0].comObjId;
			gcm.valStatus.columnId = valStatus.error[0].columnId;
			gcm.valStatus.rowIndex = valStatus.error[0].rowIndex;

			com.alert(valStatus.error[0].message, "gcm._groupValidationCallback");

		}

		return valStatus.isValid;

		function _setResult(rowIndex, dataList, gridViewObjId, columnId, message) {
			var errIdx = valStatus.error.length;
			valStatus.error[errIdx] = {};
			valStatus.error[errIdx].columnId = columnId;
			valStatus.error[errIdx].comObjId = gridViewObjId;
			valStatus.error[errIdx].columnName = dataList.getColumnName(columnId);
			valStatus.error[errIdx].rowIndex = rowIndex;
			valStatus.error[errIdx].message = com.attachPostposition(valStatus.error[errIdx].columnName) + " " + message;
		}
	} catch (e) {
		$p.log("[com.validateGridView] Exception :: " + e.message);
	} finally {
		modifiedData = null;
		modifiedIdx = null;
		dataList = null;
		gridViewObj = null;
	}
};

com.validateGridView2 = function(gridViewObj, valInfoArr, focusIndex, tacObj, tabId) {

	if (gridViewObj === null) {
		return false;
	}

	var dataList = com.getGridViewDataList(gridViewObj);
	if (dataList === null) {
		$p.log("Can not find the datalist of '" + gridViewObjId + "' object.");
		return false;
	}

	var valStatus = {
		isValid : true,
		message : "",
		error : []
	// { columnId: "", columnName: "", rowIndex: 0, message: "" }
	};

	try {
		var modifiedData = dataList.getRowJSON(focusIndex);

		for ( var valIdx in valInfoArr) {
			var valInfo = valInfoArr[valIdx];
			if ((typeof valInfo.id !== "undefined") && (typeof modifiedData[valInfo.id] !== "undefined")) {
				var value = modifiedData[valInfo.id].trim();
				if ((typeof valInfo.mandatory !== "undefined") && (valInfo.mandatory === true) && (value.length === 0)) {
					_setResult(focusIndex, dataList, gridViewObj.getID(), valInfo.id, "필수 입력 항목 입니다.");
				}
			}

			if (valStatus.error.length > 0) {
				break;
			}
		}
			
		if (valStatus.error.length > 0) {
			valStatus.isValid = false;
			valStatus.message = "유효하지 않은 값이 입력 되었습니다";

			if ((typeof tacObj !== "undefined") && (typeof tabId !== "undefined") && (tabId !== "")) {
				var tabIndex = tacObj.getTabIndex(tabId);
				tacObj.setSelectedTabIndex(tabIndex);
			}

			gcm.valStatus.isValid = false;
			gcm.valStatus.objectType = "gridView";
			gcm.valStatus.objectName = valStatus.error[0].comObjId;
			gcm.valStatus.columnId = valStatus.error[0].columnId;
			gcm.valStatus.rowIndex = valStatus.error[0].rowIndex;

			com.alert(valStatus.error[0].message, "gcm._groupValidationCallback");

		}

		return valStatus.isValid;

		function _setResult(rowIndex, dataList, gridViewObjId, columnId, message) {
			var errIdx = valStatus.error.length;
			valStatus.error[errIdx] = {};
			valStatus.error[errIdx].columnId = columnId;
			valStatus.error[errIdx].comObjId = gridViewObjId;
			valStatus.error[errIdx].columnName = dataList.getColumnName(columnId);
			valStatus.error[errIdx].rowIndex = rowIndex;
			valStatus.error[errIdx].message = com.attachPostposition(valStatus.error[errIdx].columnName) + " " + message;
		}
	} catch (e) {
		$p.log("[com.validateGridView] Exception :: " + e.message);
	} finally {
		modifiedData = null;
		modifiedIdx = null;
		dataList = null;
		gridViewObj = null;
	}
};

/**
* Grid를 Validation Check하여 해당 Table에 Focus를 주는 함수
 * @param {Object} gridViewObj GridView 객체
 * @param :{Object} tableObj Table ID
 * @param {Object[]} options 데이터 유효성 검증 옵션
 * @param {String} options[].id 유효성 검사 대상 DataCollection 컬럼 ID
 * @param {Boolean} options[].mandatory 필수 입력 값 여부
 * @param {Number} options[].minLength 최소 입력 자리수
 * @param {requestCallback} options[].valFunc 사용자 유효성 검사 함수
 * @param {Object} tacObj GridView가 포함된 TabControl 컴포넌트 객체
 * @param {String} tabId GridView가 포함된 TabControl 컴포넌트의 Tab ID
* 작성자 : 양현우
*/
com.validateGridTableView = function(gridViewObj, tableObj, tacObj, tabId) {

	if (gridViewObj === null) {
		return false;
	}
	
	var dataList = com.getGridViewDataList(gridViewObj);
	if (dataList === null) {
		$p.log("Can not find the datalist of '" + gridViewObjId + "' object.");
		return false;
	}

	var valStatus = {
		isValid : true,
		message : "",
		error : []
	// { columnId: "", columnName: "", rowIndex: 0, message: "" }
	};

	try {
		var modifiedIdx = gridViewObj.getModifiedIndex();

		for (var dataIdx = 0; dataIdx < modifiedIdx.length; dataIdx++) {
			var index = modifiedIdx[dataIdx];
			var modifiedData = dataList.getRowJSON(index);
			if (modifiedData.rowStatus === "D") {
				continue;
			}
			/*for ( var valIdx in valInfoArr) {
				var valInfo = valInfoArr[valIdx];
				if ((typeof valInfo.id !== "undefined") && (typeof modifiedData[valInfo.id] !== "undefined")) {
					var value = modifiedData[valInfo.id].trim();
					if ((typeof valInfo.mandatory !== "undefined") && (valInfo.mandatory === true) && (value.length === 0)) {
						_setResult(modifiedIdx[dataIdx], dataList, gridViewObj.getID(), valInfo.id, "필수 입력 항목 입니다.");
					} else if ((typeof valInfo.minLength !== "undefined") && (valInfo.minLength > 0) && (value.length < valInfo.minLength)) {
						_setResult(modifiedIdx[dataIdx], dataList, gridViewObj.getID(), valInfo.id, "최소 길이 " + valInfo.minLength
								+ "자리 이상으로 입력해야 합니다.");
					} else if (typeof valInfo.valFunc === "function") {
						var resultMsg = valInfo.valFunc(value, modifiedData);
						if ((typeof resultMsg !== "undefined") && (resultMsg !== "")) {
							_setResult(modifiedIdx[dataIdx], dataList, gridViewObj.getID(), valInfo.id, resultMsg);
						}
					}
				}

				if (valStatus.error.length > 0) {
					break;
				}
			}*/
			for ( var columnIdx =0; columnIdx < gridViewObj.getColumnCount(); columnIdx++) {
				try {
					var columnId = gridViewObj.getColumnID(columnIdx);
					
					if((typeof modifiedData[columnId] == "undefined")
							||(modifiedData[columnId]==null))continue;
					
					var value = modifiedData[columnId].trim();
					var userData1 = gridViewObj.getCellOption(index,columnIdx,"userData1");		
					if((value.length === 0) && (typeof userData1 !== "undefined") && (userData1.length !== 0)){		
						if (userData1 === 'M') {
							_setResult(index, dataList, gridViewObj.getID(), columnId, "필수 입력 항목 입니다.");
						}
						if (valStatus.error.length > 0) {
							break;
						}
					}
					
					var userData2 = gridViewObj.getCellOption(index,columnIdx,"userData2");	
					if((typeof userData2 !== "undefined") && (userData2.length !== 0)){
						var func = eval(userData2);
						if(func(value)==false){
	
							var resultMsg = "유효하지 않은 형식입니다.";
						
							if ((typeof resultMsg !== "undefined") && (resultMsg !== "")) {
								_setResult(index, dataList, gridViewObj.getID(), columnId, resultMsg);
							}
							if (valStatus.error.length > 0) {
								break;
							}
						}
					}
				} catch (e) {
					$p.log("[com.validateGridTableView] Exception :: " + e.message);
				}					
			}
		}

		if (valStatus.error.length > 0) {
			valStatus.isValid = false;
			valStatus.message = "유효하지 않은 값이 입력 되었습니다";

			if ((typeof tacObj !== "undefined") && (typeof tabId !== "undefined") && (tabId !== "")) {
				var tabIndex = tacObj.getTabIndex(tabId);
				tacObj.setSelectedTabIndex(tabIndex);
			}


			
			gcm.valStatus.isValid = false;
			gcm.valStatus.objectType = "gridForm";
			gcm.valStatus.objectName = valStatus.error[0].comObjId;
			gcm.valStatus.columnId = valStatus.error[0].columnId;
			gcm.valStatus.rowIndex = valStatus.error[0].rowIndex;
			gcm.valStatus.focusTable = null;
			var objArr = WebSquare.util.getChildren(tableObj, {
				excludePlugin : "group trigger textbox output calendar image span anchor pageInherit wframe itemTable",
				recursive : true
			});
			
			
			for (var objIdx in objArr) {
				var refId = objArr[objIdx].options.ref;

				if ((typeof refId == "undefined")) continue;
			
				var objRefId = refId.substring(refId.lastIndexOf('.')+1, refId.length)

			
				if (objRefId == gcm.valStatus.columnId) {
					gcm.valStatus.focusTableID = objArr[objIdx].getID();
					break;
				}
				
			}
			
			com.alert(valStatus.error[0].message, "gcm._groupValidationCallback");

		}

		return valStatus.isValid;

		function _setResult(rowIndex, dataList, gridViewObjId, columnId, message) {
			var errIdx = valStatus.error.length;
			valStatus.error[errIdx] = {};
			valStatus.error[errIdx].columnId = columnId;
			valStatus.error[errIdx].comObjId = gridViewObjId;
			valStatus.error[errIdx].columnName = dataList.getColumnName(columnId);
			valStatus.error[errIdx].rowIndex = rowIndex;
			valStatus.error[errIdx].message = com.attachPostposition(valStatus.error[errIdx].columnName) + " " + message;
			gcm.ERR_ROW_INDEX = rowIndex;
		}
	} catch (e) {
		$p.log("[com.validateGridView] Exception :: " + e.message);
	} finally {
		modifiedData = null;
		modifiedIdx = null;
		dataList = null;
		gridViewObj = null;
		objArr = null;
	}
};

/**
 * 유효성 검사 실패시 출력할 메시지를 반환한다.
 * 
 * @date 2014.12.10
 * @private
 * @memberOf com
 * @author tracom
 * @returns {String} 유효성 검사 결과 메시지
 */
com.validateMsg = function() {
	var msg = "";
	var invalidType = this.getType(); // invalid 타입
	var invalidValue = this.getValue(); // invalid 타입별 설정값

	var callerObj = null;
	if (typeof this.getCaller === "function") {
		callerObj = this.getCaller();
	} else if (typeof this.userFunc !== "undefined") {
		callerObj = $p.getComponentById(this.userFunc.arguments[1]);
	} else {
		return;
	}

	var scopeCom = gcm._getScope(callerObj).com;
	var columnName = scopeCom.getColumnName(callerObj);

	switch (invalidType) {
	case "mandatory":
		msg = scopeCom.attachPostposition(columnName) + "필수 입력 항목 입니다.";
		break;
	case "minLength":
		msg = scopeCom.attachPostposition(columnName) + "최소 길이 " + invalidValue + "자리 이상으로 입력해야 합니다.";
		break;
	case "minByteLength":
		msg = scopeCom.attachPostposition(columnName) + "최소 길이 " + invalidValue + "바이트 이상으로 입력해야 합니다.";
		break;
	default:
		msg = scopeCom.attachPostposition(columnName) + "유효하지 않은 값이 입력 되었습니다.";
		break;
	}

	if (msg !== "") {
		gcm.valStatus.isValid = false;
	}

	gcm.valStatus.objectType = "group";
	gcm.valStatus.objectName = callerObj.getID();

	scopeCom.alert(msg, "gcm._groupValidationCallback");
	return msg;
};

/**
 * 파라미터를 읽어 온다.
 * 
 * @date 2021.03.11
 * @memberOf com
 * @param {String} 파라미터 키
 * @author tracom
 * @return {Object} 파라미터 값
 * @example var code = com.getParameter("code"); // 특정 파라미터 값을 얻어오기 var param = com.getParameter(); // 전체 파라미터 값을 얻어오기
 */
com.getParameter = function(paramKey) {
	try {
		if (typeof paramKey !== "undefined") {
			var param = $p.getParameter(paramKey);
			if ((typeof param !== "undefined") && (param !== "")) {
				return param;
			}
			;

			var param = $p.getParameter("param");
			if ((typeof param !== "undefined") && (param !== "")) {
				return param[paramKey];
			}

			return param;
		} else {
			var param = $p.getParameter("param");
			return com.getJSON(param);
		}
	} catch (ex) {
		return "";
	}
};

/**
 * 현재 화면의 웹스퀘어 page 경로를 반환한다.
 * 
 * @date 2021.07.19
 * @memberOf com
 * @author tracom
 * @return {String} 현재 페이지의 경로
 * @example com.getPageUrl(); // return 예시) "/ui/BM/BM001M01.xml"
 */
com.getPageUrl = function() {
	var pArr = document.location.href.split("w2xPath=");
	var oArr = pArr[1].split("&");
	return oArr[0];
};

/**
 * 최상위 page를 index화면으로 이동 (/)
 * 
 * @date 2021.08.05
 * @memberOf com
 * @author tracom
 */
com.goHome = function() {
	if (gcm.CONTEXT_PATH == "") {
		top.window.location.href = "/";
	} else {
		top.window.location.href = gcm.CONTEXT_PATH;
	}
};

/**
 * 로그아웃으로 WAS의 사용자 session을 삭제한다.
 * 정상 처리 : /로 이동.
 * 오류 발생 : 기존 화면으로 오류 메세지 전송
 * 
 * @date 2021.08.08
 * @memberOf com
 * @author tracom
 * @example
 * com.logout();
 */
com.logout = function() {
	var logoutGrpOption = {
		id : "sbm_Logout",
		action : "/main/logout",
		target : "",
		submitDoneHandler : "com.goHome",
		isShowMeg : false
	};
	com.executeSubmission_dynamic(logoutGrpOption);
};

/**
 * contextRoot가 포함된 path를 반환한다.
 *
 * @date 2021.11.16
 * @memberOf com
 * @param {String} path 파일경로(Context가 포함되지 않은)
 * @return {String} Context가 포함된 파일경로
 * @example
 * // context가 /sample 인경우
 * com.getFullPath("/ui/SP/Parameter/confirm.xml");
 * // return 예시) "/sample/ui/SP/Parameter/confirm.xml"
 */
com.getFullPath = function(path) {
	var rtn_path = "";
	if (gcm.CONTEXT_PATH == "") {
		rtn_path = path;
	} else {
		rtn_path = gcm.CONTEXT_PATH + path;
	}
	return rtn_path;
};

/**
 * 문자열 왼쪽에 일정길이(maxLen) 만큼 '0'으로 채우기
 *
 * @date 2014.12.10
 * @param {String} str 포멧터를 적용할 문자열
 * @param {Number} maxLen 0 으로 채울 길이
 * @memberOf com
 * @author tracom
 * @return {String} 일정길이 만큼 0 으로 채워진 문자열
 * @example
 * com.fillZero("24", 4);
 * // return 예시) "0024"
 *
 * com.fillZero("11321", 8);
 * // return 예시) "00011321"
 */
com.fillZero = function(str, maxLen) {
	var len = str;
	var zero = "";

	if (typeof str == 'number')
		len = '' + str;

	if (len.length < maxLen) {
		for (var i = len.length; i < maxLen; i++) {
			zero += "0";
		}
	}
	return (zero + '' + str);
}

/**
 * JSON Object로 변환해서 반환한다.
 *
 * @date 2014.12.09
 * @param {String} str JSON 문자열
 * @memberOf com
 * @author tracom
 * @return {Object} JSON 객체 or null
 * @example
 * // 유효하지 않은 JSON 문자열 일 경우
 * com.getJSON("");
 * // return 예시)	null
 *
 * // 유효한 JSON 문자열
 * var json = '{"tbx_sPrjNm":"1","tbx_sPrtLv":"2","tbx_sReqLv":"3"}';
 * com.getJSON(json);
 * // return 예시)	{tbx_sPrjNm: "1", tbx_sPrtLv: "2", tbx_sReqLv: "3"}
 */
com.getJSON = function(str) {
	try {
		return JSON.parse(str);
	} catch (e) {
		return str;
	}
};

/**
 * XML, JSON 객체를 String 타입으로 반환한다.
 *
 * @date 2014.12.09
 * @param {Object} object String으로 변환할 JSON 객체
 * @memberOf com
 * @author tracom
 * @return {String} String으로 변환된 객체
 */
com.strSerialize = function(object) {
	if (typeof object == 'string') {
		return object;
	} else if (com.isJSON(object)) {
		return JSON.stringify(object);
	} else if (com.isXmlDoc(object)) {
		return WebSquare.xml.serialize(object);
	} else {
		return object;
	}
};

/**
 * JSON Object인지 여부를 검사한다.
 *
 * @date 2014.12.09
 * @param {Object} jsonObj JSON Object가 맞는지 검사할 JSON Object
 * @memberOf com
 * @author tracom
 * @return {Boolean} true or false
 * @example
 * com.isJSON("");
 * // return 예시) false
 * com.isJSON( {"tbx_sPrjNm": "1", "tbx_sPrtLv": "2", "tbx_sReqLv": "3"} );
 * // return 예시) true
 */
com.isJSON = function(jsonObj) {
	if (typeof jsonObj !== 'object')
		return false;
	try {
		JSON.stringify(jsonObj);
		return true;
	} catch (e) {
		return false;
	}
};

/**
 * XML Document 객체인지 여부를 검사한다.
 * 
 * @date 2014.12.09
 * @memberOf com
 * @param {Object} data XML Document 객체인지 여부를 검사한다.
 * @author tracom
 * @return {Boolean} true or false
 */
com.isXmlDoc = function(data) {
	if (typeof data != 'object')
		return false;
	if ((typeof data.documentElement != 'undefined' && data.nodeType == 9)
			|| (typeof data.documentElement == 'undefined' && data.nodeType == 1)) {
		return true;
	}
	return false;
};

/**
 * 객체의 typeof 값을 반환하며 typeof의 값이 object인 경우 array, json, xml, null로 체크하여 반환한다.
 * 
 * @date 2021.12.20
 * @param {Object} obj type을 반환 받을 객체(string,boolean,number,object 등)
 * @author tracom
 * @return {String} 객체의 타입으로 typeof가 object인 경우 array, json, xml, null로 세분화하여 반환한다. 그외 object타입이 아닌경우 원래의 type(string,boolean,number 등)을 반환한다.
 * @example
 * com.getObjectType("WebSquare");
 * // return 예시) "string"
 * com.getObjectType({"name":"WebSquare"});
 * // return 예시) "json"
 * com.getObjectType(["1","2"]);
 * // return 예시) "array"
 */
com.getObjectType = function(obj) {
	var objType = typeof obj;
	if (objType !== 'object') {
		return objType;
	} else if (obj.constructor === {}.constructor) {
		return 'json';
	} else if (obj.constructor === [].constructor) {
		return 'array';
	} else if (obj === null) {
		return 'null';
	} else {
		var tmpDoc = WebSquare.xml.parse("<data></data>");
		if (obj.constructor === tmpDoc.constructor || obj.constructor === tmpDoc.firstElementChild.constructor) {
			return 'xml';
		} else {
			return objType;
		}
	}
};

/**
 * 주민번호 문자열에 Formatter(######-#######)를 적용하여 반환한다.
 *
 * @date 2021.08.02
 * @param {String} str 주민번호 문자열
 * @memberOf com
 * @author tracom
 * @return {String} 포멧터가 적용된 주민번호 문자열
 * @example
 * com.transIdNum("1234561234567");
 * // return 예시) "123456-1234567"
 */
com.transIdNum = function(str) {
	var front = String(str).substr(0, 6);
	var back = String(str).substr(6, 7);
	var output = front + "-" + back;

	return output;
};

/**
 * 전화번호, setDisplayFormat("###-####-####") - 입력된 str에 포메터를 적용하여 반환한다.
 *
 * @date 2021.08.02
 * @param {String} str 포멧터를 적용할 문자열
 * @memberOf com
 * @author tracom
 * @return {String} 포멧터가 적용된 문자열
 * @example
 * com.transPhone("0212345678");
 * // return 예시) "02-1234-5678"
 * com.transPhone("021234567");
 * // return 예시) "02-123-4567"
 * com.transPhone("03112345678");
 * // return 예시) "031-1234-5678"
 * com.transPhone("0311234567");
 * // return 예시) "031-123-4567"
 */
com.transPhone = function(str) {
	return str.replace(/^(01[016789]{1}|02|0[3-9]{1}[0-9]{1})-?([0-9]{3,4})-?([0-9]{4})$/, "$1-$2-$3");
};

/**
 * 시간 - 입력된 String 또는 Number에 포메터를 적용하여 반환한다.
 *
 * @date 2021.08.02
 * @param {String} value 시간 Formatter를 적용한 값 (String 또는 Number 타입 지원)
 * @memberOf com
 * @author tracom
 * @return {String} 포멧터가 적용된 문자열
 * @example
 * com.transTime("123402");
 * // return 예시) "12:34:02"
 */
com.transTime = function(value) {
	var hour = String(value).substr(0, 2);
	var minute = String(value).substr(2, 2);
	var second = String(value).substr(4, 2);
	var output = hour + ":" + minute + ":" + second;

	return output;
};

/**
 * 시간 - 입력된 String 또는 Number에 포메터를 적용하여 반환한다.
 *
 * @date 2021.08.27
 * @param {String} value 시간 Formatter를 적용한 값 (String 또는 Number 타입 지원)
 * @memberOf com
 * @author tracom
 * @return {String} 포멧터가 적용된 문자열
 * @example
 * com.transTime("1234");
 * // return 예시) "12:34"
 */
com.transTime2 = function(value) {
	var output = value;
	if(value.length==4){
		var minute = String(value).substr(0, 2);
		var second = String(value).substr(2, 2);
		output = minute + ":" + second;
	}
	return output;
};


/**
 * 소수점 2자리에서 반올림 처리를 한다.
 *
 * @date 2021.08.02
 * @param {String} value 소수점 2자리 반올림 처리를 할 값 (String 또는 Number 타입 지원)
 * @memberOf com
 * @author tracom
 * @return {String} 소숫점 2자리 반올림 처리를 한 숫자 값
 * @example
 * com.transRound( "23.4567" );
 * // return 예시) "23.46"
 */
com.transRound = function(value) {
	return Math.round(Number(value) * 100) / 100;
};

/**
 * 소수점 2자리에서 내림 처리를 한다.
 *
 * @date 2021.08.02
 * @param {String} value 소수점 2자리 내림 처리를 할 값 (String 또는 Number 타입 지원)
 * @memberOf com
 * @author tracom
 * @return {Number} 소숫점 2자리 내림 처리를 한 숫자 값
 * @example
 * com.transFloor(23.4567);
 * // return 예시) 23.45
 */
com.transFloor = function(value) {
	return Math.floor(Number(str) * value) / 100;
};

/**
 * 소수점 2자리에서 올림 후 퍼센트(%)를 붙여서 반환한다.
 *
 * @date 2021.08.02
 * @param {String} value Percent(%) 포맷터를 적용할 값  (String 또는 Number 타입 지원)
 * @param {String} type 적용할 포멧터 형식
 * @memberOf com
 * @author tracom
 * @return {String} 포멧터가 적용된 문자열
 * @example
 * com.transCeil(23.4567);
 * // return 예시) 23.46
 * com.transCeil(78.567, "percent");
 * // return 예시) 78.57%
 */
com.transCeil = function(value, type) {
	var output = "";
	if (type == "percent") {
		output = Math.ceil(Number(value) * 100) / 100 + "%";
	} else {
		output = Math.ceil(Number(value) * 100) / 100;
	}
	return output;
};

/**
 * 세번째자리마다 콤마 표시, 금액, setDisplayFormat("#,###&#46##0", "fn_userFormatter2") - 입력된 str에 포메터를 적용하여 반환한다.<p>
 *
 * @date 2021.08.02
 * @param {String} value String or Number 포멧터를 적용할 값 (String 또는 Number 타입 지원)
 * @param {String} type 적용할 포멧터 형식(Default:null,dollar,plusZero,won)
 * @memberOf com
 * @author tracom
 * @return {String} 포멧터가 적용된 문자열
 * @example
 * com.transComma("12345");
 * // return 예시) 12,345
 * com.transComma("12345", "dollar");
 * // return 예시) $12,345
 * com.transComma("12345", "plusZero");
 * // return 예시) 123,450
 * com.transComma("12345", "won");
 * // return 예시) 12,345원
 */
com.transComma = function(value, type) {
	var amount;

	if (type == "plusZero") {
		amount = new String(value) + "0";
	} else {
		amount = new String(value);
	}

	amount = amount.split(".");

	var amount1 = amount[0].split("").reverse();
	var amount2 = amount[1];

	var output = "";
	for (var i = 0; i <= amount1.length - 1; i++) {
		output = amount1[i] + output;
		if ((i + 1) % 3 == 0 && (amount1.length - 1) !== i)
			output = ',' + output;
	}

	if (type == "dollar") {
		if (!amount2) {
			output = "$ " + output;
		} else {
			output = "$ " + output + "." + amount2;
		}
	} else if (type == "won") {
		if (!amount2) {
			output = output + "원";
		} else {
			output = output + "." + amount2 + "원";
		}
	} else {
		if (!amount2) {
			output = output;
		} else {
			output = output + "." + amount2;
		}
	}

	return output;
}

/**
 * 텍스트 - 입력된 str에 포메터를 적용하여 반환한다.
 * 
 * @date 2021.08.02
 * @param {String} str String or Number 포멧터를 적용할 값
 * @memberOf com
 * @author tracom
 * @return {String} 포멧터가 적용된 문자열
 * @example 
 * com.transText("1"); 
 * // return 예시) 1 
 * com.transText("12");
 * // return 예시) 12 
 * com.transText("123"); 
 * // return 예시) 1.23
 * com.transText("1234"); 
 * // return 예시) 12.34
 * com.transText("12345"); 
 * // return 예시) 123.345
 * com.transText("123456"); 
 * // return 예시) 1234.56
 * com.transText("1234567"); 
 * // return 예시) 12345.67
 */
com.transText = function(str) {
	var amount = new String(str);
	var result;

	if (amount.length < 3) {
		result = amount.substr(0, amount.length);
	} else {
		result = amount.substr(0, amount.length - 2) + "." + amount.substr(amount.length - 2, amount.length);
	}
	return result;
}

/**
 * 날짜 - 입력된 str에 포메터를 적용하여 반환한다.
 *
 * @date 2021.08.02
 * @param {String} str 포멧터를 적용할 파라메터 (String 또는 Number 타입 지원)
 * @param {String} type 적용할 포멧터 형식 Default:null,slash,date
 * @memberOf com
 * @author tracom
 * @return {String} 포멧터가 적용된 문자열
 * @example
 * com.transDate(20120319, "slash");
 * // return 예시) 12/03/19
 * com.transDate(20120319, "date");
 * // return 예시) 2012/03/19
 * com.transDate(20120319, "colon");
 * // return 예시) 2012:03:19
 * com.transDate(20120319);
 * // return 예시) 2012년 03월 19일
 */
com.transDate = function(str, type) {
	var output = "";
	var date = new String(str);

	if (type == "slash") {
		date = date.substring(2, date.length);
		for (var i = 0; i <= date.length - 1; i++) {
			output = output + date[i];
			if ((i + 1) % 2 == 0 && (date.length - 1) !== i)
				output = output + "/";
		}
	} else if (type == "date") {
		if (date.length == 8) {
			output = date.substr(0, 4) + "/" + date.substr(4, 2) + "/" + date.substr(6, 2);
		}
	} else if (type == "colon") {
		if (date.length == 8) {
			output = date.substr(0, 4) + ":" + date.substr(4, 2) + ":" + date.substr(6, 2);
		}
	} else {
		var year = date.substr(0, 4);
		var month = date.substr(4, 2);
		var day = date.substr(6, 2);
		var output = year + "년 " + month + "월 " + day + "일";
	}
	return output;
};

/**
 * displayFormatter - 입력된 str에 포메터를 적용하여 반환한다.
 *
 * @date 2021.08.03
 * @param {String} str 포멧터를 적용할 문자열
 * @memberOf com
 * @author tracom
 * @return {String} 포멧터가 적용된 문자열
 * @example
 * com.transUpper("google.com");
 * // return 예시) "GOOGLE.COM"
 */
com.transUpper = function(str) {
	return str.toUpperCase();
};

/**
 * 문자(char)의 유형을 리턴한다.
 *
 * @date 2021 08.02
 * @param {String} str 어떤 유형인지 리턴받을 문자
 * @memberOf com
 * @author tracom
 * @return {Number} 유니코드 기준 <br><br>
 * 한글음절[ 44032 ~ 55203 ] => 1 <br>
 * 한글자모[ 4352 ~ 4601 ] => 2 <br>
 * 숫자[ 48 ~ 57 ] => 4 <br>
 * 특수문자[ 32 ~ 47 || 58 ~ 64 || 91 ~ 96 || 123 ~ 126 ] => 8 <br>
 * 영문대[ 65 ~ 90 ] => 16 <br>
 * 영문소[ 97 ~ 122 ] => 32 <br>
 * 기타[그외 나머지] => 48
 * @example
 * com.getLocale("가");
 * // return 예시)1
 * com.getLocale("ㅏ");
 * // return 예시)2
 * com.getLocale("1");
 * // return 예시)4
 * com.getLocale("!");
 * // return 예시)8
 * com.getLocale("A");
 * // return 예시)16
 * com.getLocale("a");
 * // return 예시)32
 * com.getLocale("¿");
 * // return 예시)48
 */
com.getLocale = function(str) {
	var locale = 0;
	if (str.length > 0) {
		var charCode = str.charCodeAt(0);

		if (charCode >= 0XAC00 && charCode <= 0XD7A3) { // 한글음절.[ 44032 ~ 55203 ]
			locale = 0X1; // 1
		} else if ((charCode >= 0X1100 && charCode <= 0X11F9) || (charCode >= 0X3131 && charCode <= 0X318E)) { // 한글자모.[ 4352 ~ 4601 ]
			locale = 0X2; // 2
		} else if (charCode >= 0X30 && charCode <= 0X39) { // 숫자.[ 48 ~ 57 ]
			locale = 0X4; // 4
		} else if ((charCode >= 0X20 && charCode <= 0X2F) || (charCode >= 0X3A && charCode <= 0X40)
				|| (charCode >= 0X5B && charCode <= 0X60) || (charCode >= 0X7B && charCode <= 0X7E)) { // 특수문자
			locale = 0X8; // 8
		} else if (charCode >= 0X41 && charCode <= 0X5A) { // 영문 대.[ 65 ~ 90 ]
			locale = 0X10; // 16
		} else if (charCode >= 0X61 && charCode <= 0X7A) { // 영문 소.[ 97 ~ 122 ]
			locale = 0X20; // 32
		} else { // 기타
			locale = 0X30; // 48
		}
	}
	return locale;
};

/**
 * 입력받은 문자열이 한글이면 true, 아니면 false를 리턴한다.
 * 
 * @date 2021.08.02
 * @param {String} str 한글 유형인지 검증할 문자열
 * @memberOf com
 * @author tracom
 * @return {Boolean} true or false
 * @example 
 * com.isKorean(""); 
 * // return 예시) false 
 * com.isKorean("무궁화꽃이"); 
 * // return 예시) true
 */
com.isKorean = function(str) {
	if (str != null && str.length > 0) {
		var locale = 0;
		for (var i = 0; i < str.length; i++) {
			locale = com.getLocale(str.charAt(i));
		}
		if ((locale & ~0x3) == 0) {
			return true;
		}
	}
	return false;
};

/**
 * 종성이 존재하는지 여부를 검사한다.
 * 
 * @date 2021.08.02
 * @param {String} str 종성의 여부를 검사할 문자열
 * @memberOf com
 * @author tracom
 * @return {Boolean} true or false
 * @example 
 * com.isFinalConsonant("종서") 
 * // return 예시) false 
 * com.isFinalConsonant("종성") 
 * // return 예시) true 
 * com.isFinalConsonant("입니다") 
 * // return 예시) false
 *	com.isFinalConsonant("입니당") 
 * // return 예시) true
 */
com.isFinalConsonant = function(str) {
	var code = str.charCodeAt(str.length - 1);
	if ((code < 44032) || (code > 55197)) {
		return false;
	}
	if ((code - 16) % 28 == 0) {
		return false;
	}
	return true;
};

/**
 * 단어 뒤에 '은'이나 '는'을 붙여서 반환한다.
 * 
 * @date 2021.08.02
 * @param {String} str 은, 는 붙일 문자열
 * @memberOf com
 * @author tracom
 * @return {String} 변환된 문자열
 * @example 
 * com.attachPostposition("나"); 
 * // return 예시)"나는" 
 * com.attachPostposition("나와 너"); 
 * // return 예시)"나와 너는" 
 * com.attachPostposition("그래서"); 
 * // return 예시)"그래서는" 
 * com.attachPostposition("그랬습니다만"); 
 * // return 예시)"그랬습니다만은"
 */
com.attachPostposition = function(str) {
	if (com.isFinalConsonant(str)) {
		str = str + "은 ";
	} else {
		str = str + "는 ";
	}
	return str;
};

/**
 * 입력받은 문자열에 한글이 포함되어 있으면 true, 아니면 false를 리턴한다.
 * 
 * @date 2021.08.02
 * @param {String} str 한글이 포함되어 있는지 검증 받을 문자열
 * @memberOf com
 * @author tracom
 * @return {Boolean} true or false
 * @example 
 * com.isKoreanWord("abcd무궁화"); //return 예시) true
 * com.isKoreanWord("abcd"); //return 예시) false
 */
com.isKoreanWord = function(str) {
	var c;
	for (var i = 0; i < str.length; i++) {
		c = str.charAt(i);
		if (com.isKorean(c)) {
			return true;
		}
	}
	return false;
};

/**
 * 사업자번호 유효성을 검사한다.
 * 
 * @date 2014.12.10
 * @memberOf com
 * @param {String} str 사업자번호 문자열
 * @returns {Boolean} 올바른 번호가 아닌경우 false
 * @example com.checkBizID("1102112345");
 */
com.checkBizID = function(str) {
	var sum = 0;
	var aBizID = new Array(10);
	var checkID = new Array("1", "3", "7", "1", "3", "7", "1", "3", "5");

	for (var i = 0; i < 10; i++) {
		aBizID[i] = str.substring(i, i + 1);
	}
	for (var i = 0; i < 9; i++) {
		sum += aBizID[i] * checkID[i];
	}
	sum = sum + parseInt((aBizID[8] * 5) / 10);
	temp = sum % 10;
	temp1 = 0;

	if (temp != 0) {
		temp1 = 10 - temp;
	} else {
		temp1 = 0;
	}
	if (temp1 != aBizID[9]) {
		return false;
	}
	return true;
};

/**
 * 법인등록번호 유효성을 검사한다.
 * 
 * @date 2014. 12. 10.
 * @memberOf com
 * @param {String} str 문자열
 * @returns {Boolean} 올바른 번호가 아닌경우 false
 * @example com.checkCorpID("110211234567");
 */
com.checkCorpID = function(str) {
	var checkID = new Array(1, 2, 1, 2, 1, 2, 1, 2, 1, 2, 1, 2);
	var sCorpNo = str;
	var lV1 = 0;
	var nV2 = 0;
	var nV3 = 0;

	for (var i = 0; i < 12; i++) {
		lV1 = parseInt(sCorpNo.substring(i, i + 1)) * checkID[i];

		if (lV1 >= 10) {
			nV2 += lV1 % 10;
		} else {
			nV2 += lV1;
		}
	}
	nV3 = nV2 % 10;

	if (nV3 > 0) {
		nV3 = 10 - nV3;
	} else {
		nV3 = 0;
	}
	if (sCorpNo.substring(12, 13) != nV3) {
		return false;
	}
	return true;
};

/**
 * 내외국인 주민등록번호 유효성을 검사한다.
 * 
 * @date 2014. 12. 10.
 * @memberOf com
 * @param {String} str 문자열
 * @returns {Boolean} 올바른 번호가 아닌경우 false
 * @example com.checkPersonID("9701011234567");
 */
com.checkPersonID = function(str) {
	var checkID = new Array(2, 3, 4, 5, 6, 7, 8, 9, 2, 3, 4, 5);
	var i = 0, sum = 0;
	var temp = 0;
	var yy = "";

	if (str.length != 13) {
		return false;
	}
	for (i = 0; i < 13; i++) {
		if (str.charAt(i) < '0' || str.charAt(i) > '9') {
			return false;
		}
	}

	// foreigner PersonID Pass
	if (str.substring(6, 13) == "5000000" || str.substring(6, 13) == "6000000" || str.substring(6, 13) == "7000000"
			|| str.substring(6, 13) == "8000000") {
		return true;
	}
	for (i = 0; i < 12; i++) {
		sum += str.charAt(i) * checkID[i];
	}
	temp = sum - Math.floor(sum / 11) * 11;
	temp = 11 - temp;
	temp = temp - Math.floor(temp / 10) * 10;

	// 나이 (-) 체크
	if (str.charAt(6) == '1' || str.charAt(6) == '2' || str.charAt(6) == '5' || str.charAt(6) == '6') {
		yy = "19";
	} else {
		yy = "20";
	}

	if (parseInt($p.getCurrentServerDate('yyyy')) - parseInt(yy + str.substring(0, 2)) < 0) {
		return false;
	}

	// 외국인 주민번호 체크로직 추가
	if (str.charAt(6) != '5' && str.charAt(6) != '6' && str.charAt(6) != '7' && str.charAt(6) != '8') {
		if (temp == eval(str.charAt(12))) {
			return true;
		} else {
			return false;
		}
	} else {
		if ((temp + 2) % 10 == eval(str.charAt(12))) {
			return true;
		} else {
			return false;
		}
	}
	return false;
};

/**
 * 메일주소 체크한다.
 * 
 * @date 2014. 12. 10.
 * @memberOf com
 * @param {String} str 메일주소
 * @return {Boolean} 정상이면 공백("")을 반환, 그외는 에러 메시지 반환
 * @example com.isEmail("emailTest@email.com")
 */
com.checkEmail = function(str) {
	if (typeof str != "undefined" && str != "") {
		var format = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;

		if (format.test(str)) {
			return true;
		} else {
			return false;
		}
	}
	return true;
};

/**
 * 데이터 리스트 필터 설정 함수.
 * 
 * @date 2021.03.13
 * @memberOf com
 * @author tracom
 * @param {String} _dataComp : 필터 설정 데이터콜렉션 객체ID(dataList 혹은 linkedDataList)
 * @param {String} _condition : 필터 상태값
 * @param {Boolean} _append targetPgCd : 설정 필터 추가 혹은 재정의 [default:true (추가), false(재정의)]
 * @param {String} options : 필터 상태값이 'LIKE' 조건일 경우 추가 옵션
 * @param {String} options : 필터 상태값이 'LIKE' 조건일 경우 추가 옵션
 * @param {String} options.colId : LIKE 검색 컬럼 ID
 * @param {String} options.func : LIKE 검색 조건 실행 함수명
 * @returns {Object} 필터 결과 데이터
 */
com.dataListFilter = function(_dataComp, _condition, _append, options) {
	try {
		var filterDs = $p.getComponentById(_dataComp);
		var filterDsId = filterDs.getID();
		var conditions = _condition;
		var append = (typeof _append == "undefined") ? true : _append;
		var cols = conditions.split(/\W+/g);
		var uniqCols = new Array();
		var sortArr = [];

		// 필터 컬럼 추출.
		$.each(cols, function(i, el) {
			if (el != "" && uniqCols.indexOf(el) == -1 && typeof filterDs.getColumnIndex(el) != "undefined") {
				uniqCols.push(el);
			}
		});

		// 문자열 길이로 재정렬
		uniqCols.sort(function(a, b) {
			if (a.length < b.length)
				return 1;
			if (a.length > b.length)
				return -1;
			return 0;
		});

		// 필터 설정 전 sort 상태 확인.
		if (filterDs && filterDs.sortStatusArr) {
			sortArr = filterDs.sortStatusArr;
		}

		// Like 필터 기능.
		if (typeof conditions == "string" && conditions.toUpperCase() == "LIKE") {
			if (options && typeof options.colId != "undefined" && typeof options.func != "undefined") {
				if (typeof filterDs.getColumnIndex(options.colId) == "undefined") {
					com.alert("like 사용자지정 필터 컬럼이 존재하지 않습니다.");
				} else if (typeof options.func == "function") {
					var isAppend = WebSquare.util.getBoolean(append) ? true : false;
					if (!isAppend) {
						filterDs.removeColumnFilterAll();
					}
					var func = eval(options.func);
					if (typeof func == "function") {
						filterDs.setColumnFilter({
							type : 'func',
							colIndex : options.colId,
							key : func,
							condition : 'and'
						});
					}
				}
			} else {
				com.alert("like 사용자지정 필터 값이 잘못되었습니다.");
			}
		} else { // 일반필터 기능.
			var conditionArr = conditions.split(/[&]{2}|[|]{2}/);
			var conditionArr1 = new Array();
			var conditionArr2 = conditions.match(/[&]{2}|[|]{2}/g);

			// 필터조건 [|| , &&] 구분.
			for (var i = 0; i < conditionArr.length; i++) {
				if ((conditionArr[i] + "").trim() != "") {
					conditionArr1.push(conditionArr[i]);
				}
			}

			// 필터조건 정의.
			for (var i = 0; i < conditionArr1.length; i++) {
				var cond = conditionArr1[i];
				// cond = cond.replace(/\s/gi,"");
				var valArr = [ cond ];
				var xBy = "";
				if (cond.indexOf("==") > -1) {
					valArr = cond.split("==");
					xBy = "==";
				} else if (cond.indexOf("!=") > -1) {
					valArr = cond.split("!=");
					xBy = "!=";
				} else if (cond.indexOf(">") > -1) {
					valArr = cond.split(">");
					xBy = ">";
				} else if (cond.indexOf("<") > -1) {
					valArr = cond.split("<");
					xBy = "<";
				} else if (cond.indexOf(">=") > -1) {
					valArr = cond.split(">=");
					xBy = ">=";
				} else if (cond.indexOf("<=") > -1) {
					valArr = cond.split("<=");
					xBy = "<=";
				}

				for (var j = 0; j < valArr.length; j++) {
					var chgCnt = -1;
					for (var k = 0; k < uniqCols.length; k++) {
						var deQ = (valArr[j] + "").trim();
						if (deQ.indexOf(uniqCols[k]) > -1) {
							valArr[j] = valArr[j].replace(uniqCols[k], "####");
							chgCnt = k;
							break;
						}
					}
					if (chgCnt > -1)
						valArr[j] = valArr[j].replace(/####/g, filterDsId + ".getCellData(" + filterDsId + ".getFilteredRowIndex(rowIdx),'"
								+ uniqCols[chgCnt] + "')");
				}

				if (xBy) {
					conditionArr1[i] = valArr[0] + xBy + valArr[1];
				} else {
					conditionArr1[i] = valArr[0];
				}
			}

			var returnConditions = "";
			if (conditionArr2 != null) {
				for (var i = 0; i < conditionArr1.length; i++) {
					if (typeof conditionArr2[i] !== "undefined") {
						if (typeof conditionArr1[i + 1] == "undefined" || conditionArr1[i + 1] == null
								|| (conditionArr1[i + 1] + "").trim() == "") {
							returnConditions += conditionArr1[i];
						} else {
							returnConditions += conditionArr1[i] + conditionArr2[i];
						}
					} else {
						returnConditions += conditionArr1[i];
					}
				}
			} else {
				for (var i = 0; i < conditionArr1.length; i++) {
					returnConditions += conditionArr1[i];
				}
			}

			// 필터조건 새로정의 또는 필터 조건추가 여부.
			var isAppend = WebSquare.util.getBoolean(append) ? true : false;
			if (!isAppend) {
				filterDs.removeColumnFilterAll();
			}

			// 필터 설정.
			filterDs.setColumnFilter({
				type : 'func',
				colIndex : 0,
				key : function(cellData, tmpParam, rowIdx) {
					try {
						if (eval(returnConditions)) {
							return true;
						} else {
							return false;
						}
					} catch (e) {
						console.log("[filter.setColumnFilter] Exception returnConditions :: " + returnConditions, e);
						return true;
					}
				},
				condition : 'and'
			});
		}

		// 정의 필터 이전 sort 재반영.
		if (sortArr.length > 1) {
			var sortIndex = "";
			var sortOrder = "";
			for (var i = 0; i < sortArr.length; i++) {
				var colId = sortArr[i].colID;
				var sortOrd = sortArr[i].sortOrder;
				if (filterDs.getColumnID(colId) && sortOrd) {
					if (sortIndex == "") {
						sortIndex += colId;
					} else {
						sortIndex += " " + colId;
					}

					if (sortOrder == "") {
						sortOrder += sortOrd;
					} else {
						sortOrder += " " + sortOrd;
					}
				}
			}
			filterDs.multisort({
				"sortIndex" : sortIndex,
				"sortOrder" : sortOrder
			});
		} else if (sortArr.length == 1) {
			var sortCol = sortArr[0].colID;
			var sortOrder = sortArr[0].sortOrder; // 1 : 오름차순, -1 : 내림차순
			if (sortOrder == 1) {
				sortOrder = 0;
			} else if (sortOrder == -1) {
				sortOrder = 1;
			} else {
				sortOrder = 2;
			}
			filterDs.sort(sortCol, sortOrder); // 0이면 오름차순 1이면 내림차순 2이면 정렬을 취소
		}

		return filterDs.getAllFilteredJSON();
	} catch (e) {

	}
};

/**
 * 컴포넌트 설정 이벤트 중지 함수.
 * 
 * @date 2021.03.13
 * @memberOf com
 * @author tracom
 * @param {String} _targetComp : 설정 컴포넌트 객체ID
 * @param {Boolean} _flag : 이벤트 설정 여부 값 [default: false(실행), true(중지)]
 * @param {Object} _eventList : 중지 이벤트 리스트 값(배열) [default:null (모든 이벤트)]
 */
com.setEventPause = function(_targetComp, _flag, _eventList) {
	try {
		var comp = $p.getComponentById(_targetComp);
		var flag = WebSquare.util.getBoolean(_flag);
		var eventList = typeof _eventList != "undefined" ? _eventList : null;
		if (typeof comp == "undefined")
			return -1;

		if (comp.options.pluginName == "dataList") {
			comp.setBroadcast(flag);

			if (flag) {
				comp.broadcast({
					"linkedDataList" : [ "notifyDataChanged" ],
					"gridView" : [ "notifyDataChanged" ],
					"generalComp" : "both"
				});
			}

			comp.setEventPause(eventList, flag);
			for ( var i in comp.childCompHash) {
				var childComp = ngmf.object(comp.childCompHash[i].id);
				if (typeof childComp != "undefined") {
					childComp.setEventPause(eventList, flag);
				}
			}

			for ( var i in comp.refCompHash) {
				var refComp = ngmf.object(comp.refCompHash[i].id);
				if (typeof refComp != "undefined") {
					refComp.setEventPause(eventList, flag);
				}
			}

			if (flag) {
				WebSquare.event.fireEvent(comp, "ondataload");
			}
		} else {
			comp.setEventPause(eventList, !flag);
		}
	} catch (e) {

	}
};

/**
 * 실행 프레임 정보 조회
 * 
 * @date 2021.03.13
 * @memberOf com
 * @author tracom
 * @returns {Object} 현재 Active Window 정보 반환
 * @returns {String} activeinfo.type : 액티브 윈도우 타입 [P : 팝업, T: 탭컨텐츠, W: 윈도우컴포넌트]
 * @returns {Object} activeinfo.window : 액티브 윈도우 객체
 * @returns {String} activeinfo.programCd : 액티브 윈도우 프로그램 코드
 */
com.getActiveWindowInfo = function() {
	var activeInfo = {
		"type" : "", // T:Tabcontrol, W:windowContainer, P:popup
		"window" : "", // Window객체
		"programCd" : "" // 프로그램 코드 (팝업인경우는 예외)
	};
	try {
		var domActiveElement = document.activeElement;
		var isPopup = $p.top().$p.getComponentById(domActiveElement.id);
		var popupList = WebSquare.uiplugin.popup.popupList;
		if (popupList.length > 0) {
			for (var i = popupList.length - 1; i > -1; i--) {
				if (WebSquare.uiplugin.popup.popupList[i].options.modal) {
					isPopup = WebSquare.uiplugin.popup.popupList[i].popupWin;
				}
			}
		}

		if (isPopup && popupList.length > 0) {
			// 팝업이 현재 열려있는상태.
			activeInfo["type"] = "P";
			activeInfo["window"] = isPopup.frame.getWindow();
		} else {
			activeInfo["type"] = $p.top().scwin.getLayoutId();
			if (activeInfo["type"] == "T") {
				var selectedTabId = $p.top().tac_layout.getSelectedTabID();
				var findProgramList = gcm.menuComp.getMatchedJSON("MENU_CD", selectedTabId, true);
				if (findProgramList.length > 0) {
					activeInfo["programCd"] = findProgramList[0].PROG_CD;
				}
				activeInfo["window"] = $p.top().tac_layout.getWindow(selectedTabId);
			} else if (activeInfo["type"] == "M") {
				var selectedWindowId = $p.top().wdc_main.getSelectedWindowId();
				var findProgramList = gcm.menuComp.getMatchedJSON("MENU_CD", selectedWindowId, true);
				if (findProgramList.length > 0) {
					activeInfo["programCd"] = findProgramList[0].PROG_CD;
				}
				activeInfo["window"] = $p.top().wdc_main.getWindow(selectedWindowId);

			}
		}
	} catch (e) {

	}
	return activeInfo;
}

/**
 * form write 권한 세팅
 * 
 * @date 2021.03.13
 * @memberOf com
 * @author tracom
 * @param {Object} frm 해당 form
 * @param {Boolean} yn readOnly로 할지(y이면 readOnly)
*/
com.enableWriteForm = function(frm,yn) {
	try{
		var objArr = WebSquare.util.getChildren(frm, {
			excludePlugin : "group trigger textbox output calendar image span anchor pageInherit wframe itemTable",
			recursive : true
		});
		
		for (var objIdx in objArr) {
			//objArr[objIdx].options.readOnly = yn;
			objArr[objIdx].setReadOnly(yn);
		}
	} catch (e) {
		
	}
}

/**
 * 권한에 따른 쓰기 권한 처리(저장 권한이 없으면 쓰기 동작 하지 않음)
 * 
 * @date 2021.03.13
 * @memberOf com
 * @author tracom
 * @param {autoOpt} 현재 화면에 세팅된 grid 정보
*/
com.enableDisp = function(autoOpt) {
	var programAuthority = gcm.CUR_PROGRAM_AUTH;
	
	if(programAuthority.SAV_AH != 'Y'){
		if((typeof autoOpt !== "undefined")&&(autoOpt !== null)){
			var main = autoOpt.Main;
			if((typeof main !== "undefined")&&(main !== null)){
				if((typeof main.grid !== "undefined")&&(main.grid !== null))main.grid.options.readOnly = true;
				if((typeof main.frm !== "undefined")&&(main.frm !== null))com.enableWriteForm(main.frm,true);
				
			}
			
			var sub = autoOpt.Sub1;
			if((typeof sub !== "undefined")&&(sub !== null)&&(typeof sub.grid !== "undefined")&&(sub.grid !== null)){
				sub.grid.options.readOnly = true;
			}
		}
	}	
}

/**권한에 따른 메인 버튼 생성
 * 
 * @date 2021.03.13
 * @memberOf com
 * @author tracom
 * @param {Object} btnOptions 버튼 정보
 * @param {Object} generator 버튼 정보를 화면에 출력할 generator window
*/
com.setMainBtn = function(btnOptions, generator) {

	var programAuthority = gcm.CUR_PROGRAM_AUTH;
	
	if(programAuthority.AUTH_CHECK != 'Y')return;
	
	for(var i=0; i<btnOptions.length; i++){
		try {
			var item = btnOptions[i];
			if(eval("programAuthority."+item.auth.value) == "Y"){
				var tmpParentIdx = generator.insertChild();
				var mainBtn = generator.getChild(tmpParentIdx, "btn_main");
				var str = "";
				if(item.userStr !== "undefined" && item.userStr!="" && item.userStr != null){
					str = item.userStr;
				}
				else{
					str = item.auth.str;
				}
					mainBtn.setValue(str);
				mainBtn.addClass(item.auth.class);
				if (typeof item.cbFnc === "function") {
					mainBtn.bind("onclick", item.cbFnc); 
				}
			}
		} catch (e) {
			
		}
	}
}
/**권한에 따른 메인 버튼 생성(자동, 사용자 옵션에 따라 버튼 생성)
 * 
 * @date 2021.03.13
 * @memberOf com
 * @author tracom
 * @param {} wfm_mainBtn main 버튼 window
 * @param {} type 화면 유형
 * @param {} autoOpt 화면 자동 옵션
 * @param {} usrOpt 사용자 옵션
 * 
 사용 예
var userOptions = {
	SEARCH: function () {
    	com.searchGrid(SI0101G0,sub_SI0101G0R0,sub_SI0101G0S0);
    }
};

var autoOptions = {
	Main : { grid : SI0101G0, srchSbm : sub_SI0101G0R0, savSbm : sub_SI0101G0S0, keySbm : sub_SI0101G0K0 }
 };
com.setMainBtn2(wfm_mainBtn, btnCom.TYPE.SINGLE_GRID, autoOptions, userOptions);
*/
com.setMainBtn2 = function(wfm_mainBtn,type, autoOpt, usrOpt, codeOptions) {
	
	var programAuthority = gcm.CUR_PROGRAM_AUTH;
	com.enableDisp(autoOpt);
	
	com.initGridInfo(autoOpt); //그리드 항목명 저장
	
	if(programAuthority.AUTH_CHECK != 'Y')return;
	
	wfm_mainBtn.getWindow().btn_main_generator.removeAll();
	
	for(var i in gcm.BTN){
		try {
			var item = gcm.BTN[i];
			if(eval("programAuthority."+item.value) == "Y"){
				
				if ((typeof usrOpt !== "undefined")&&(usrOpt !== null)&&
						((typeof eval("usrOpt."+i) === "function")||(typeof usrOpt[i] !== "undefined"))) {
					var main = autoOpt.Main;
					if (typeof eval("usrOpt."+i) === "function") {
						var tmpParentIdx = wfm_mainBtn.getWindow().btn_main_generator.insertChild();
						var mainBtn = wfm_mainBtn.getWindow().btn_main_generator.getChild(tmpParentIdx, "btn_main");
						var str = item.str;
						mainBtn.setValue(str);
						mainBtn.addClass(item.class);
						mainBtn.bind("onclick", eval("usrOpt."+i));
						if ((i == gcm.BTN.SEARCH.nm)&&(typeof main.srchGrp !== "undefined")&&(main.srchGrp !== null)){
							com.setEnterKeyEvent(main.srchGrp, eval("usrOpt."+i));
						}
					}
					else if((typeof eval(usrOpt[i].cbFnc) === "function")||(typeof usrOpt[i].nm !== "undefined")
							||(typeof usrOpt[i].class !== "undefined")){
						var tmpParentIdx = wfm_mainBtn.getWindow().btn_main_generator.insertChild();
						var mainBtn = wfm_mainBtn.getWindow().btn_main_generator.getChild(tmpParentIdx, "btn_main");
						if(typeof eval(usrOpt[i].cbFnc) === "function"){
							mainBtn.bind("onclick", eval(usrOpt[i].cbFnc));
							if ((i == gcm.BTN.SEARCH.nm)&&(typeof main.srchGrp !== "undefined")&&(main.srchGrp !== null)){
								com.setEnterKeyEvent(main.srchGrp, eval(usrOpt[i].cbFnc));
							}
						}
						if(usrOpt[i].nm !== "undefined"){
							mainBtn.setValue(usrOpt[i].nm);
						}
						if(usrOpt[i].class !== "undefined"){
							mainBtn.addClass(usrOpt[i].class);
						}
					}
					else{
						
					}
				}
				else if((typeof autoOpt !== "undefined")&&(autoOpt !== null)){
					var tmpParentIdx = wfm_mainBtn.getWindow().btn_main_generator.insertChild();
					var mainBtn = wfm_mainBtn.getWindow().btn_main_generator.getChild(tmpParentIdx, "btn_main");
					var str = item.str;
					mainBtn.setValue(str);
					mainBtn.addClass(item.class);
					
					var main = autoOpt.Main;
				
					if( type == gcm.DISP_TYPE.SINGLE_GRID){ //단일 그리드
						if(i == gcm.BTN.SEARCH.nm){
							item.cbFnc = function(){
								com.searchGrid(main.grid, main.srchSbm , main.savSbm);
							}
						}
						else if(i == gcm.BTN.ADD.nm){
							item.cbFnc = function(){
								if ((typeof main.keySbm !== "undefined")&&(main.keySbm !== null)){
									$p.executeSubmission(main.keySbm);
								}
								else{
									com.addGrid2(main.grid,main.focusColumn);
								}
							}
						}
						else if(i == gcm.BTN.DEL.nm){
							item.cbFnc = function(){
								com.delGrid(main.grid);
							}
						}
						else if(i==gcm.BTN.CNL.nm){
							item.cbFnc = function(){
								com.cancelGrid(main.grid);
							}
						}
						else if(i==gcm.BTN.SAVE.nm){
							item.cbFnc = function(){
								com.saveGrid(main.grid, main.savSbm);
							}
						}
						else if(i==gcm.BTN.EXL_I.nm){
							item.cbFnc = function(){
								com.exlUploadGrid(main.grid, codeOptions);
							}
						}
						else if(i==gcm.BTN.EXL.nm){
							item.cbFnc = function(){
								com.exlGrid(main.grid);
							}
						}
						else if(i==gcm.BTN.EXL_F.nm){
							item.cbFnc = function(){
								com.exlFrmGrid(main.grid);
							}
						}
						else if(i==gcm.BTN.CLOSE.nm){
							item.cbFnc = function(){
								com.closeTab(main.grid);
							}
						}
					}
					else if( type == gcm.DISP_TYPE.SINGLE_GRID_FORM){ //단일 그리드와 폼
						if(i == gcm.BTN.SEARCH.nm){
							item.cbFnc = function(){
								com.searchGridForm(main.grid, null, main.srchSbm , main.savSbm);
							}
						}
						else if(i == gcm.BTN.ADD.nm){
							item.cbFnc = function(){
								if ((typeof main.keySbm !== "undefined")&&(main.keySbm !== null)){
									$p.executeSubmission(main.keySbm);
								}
								else{
									com.addGrid2(main.grid,main.focusColumn);
								}
							}
						}
						else if(i == gcm.BTN.DEL.nm){
							item.cbFnc = function(){
								com.delGrid(main.grid);
							}
						}
						else if(i==gcm.BTN.CNL.nm){
							item.cbFnc = function(){
								com.cancelGrid(main.grid);
							}
						}
						else if(i==gcm.BTN.SAVE.nm){
							item.cbFnc = function(){
								com.saveGridForm(main.grid, main.frm, main.savSbm);
							}
						}
						else if(i==gcm.BTN.EXL_I.nm){
							item.cbFnc = function(){
								com.exlUploadGrid(main.grid, codeOptions);
							}
						}
						else if(i==gcm.BTN.EXL.nm){
							item.cbFnc = function(){
								com.exlGrid(main.grid);
							}
						}
						else if(i==gcm.BTN.EXL_F.nm){
							item.cbFnc = function(){
								com.exlFrmGrid(main.grid);
							}
						}
						else if(i==gcm.BTN.CLOSE.nm){
							item.cbFnc = function(){
								com.closeTab(main.grid);
							}
						}
					}
					else if( type == gcm.DISP_TYPE.DUAL_GRID){ //듀얼 그리드
						if(i == gcm.BTN.SEARCH.nm){
							item.cbFnc = function(){
								var sub = autoOpt.Sub1;
								if ((typeof sub !== "undefined")&&(sub !== null)){
									com.searchDualGrid(main.grid, sub.grid, null, main.srchSbm, main.savSbm, main.allSavSbm, sub.savSbm)
								}
								else{
									com.searchGrid(main.grid, main.srchSbm , main.savSbm);
								}
							}
						}
						else if(i == gcm.BTN.ADD.nm){
							item.cbFnc = function(){
								if ((typeof main.keySbm !== "undefined")&&(main.keySbm !== null)){
									$p.executeSubmission(main.keySbm);
								}
								else{
									com.addGrid2(main.grid,main.focusColumn);
								}
							}
						}
						else if(i == gcm.BTN.DEL.nm){
							item.cbFnc = function(){
								var sub = autoOpt.Sub1;
								if ((typeof sub !== "undefined")&&(sub !== null)){
									com.delDualGrid(main.grid, sub.grid);
								}
								else {
									com.delGrid(main.grid);
								}
							}
						}
						else if(i==gcm.BTN.CNL.nm){
							item.cbFnc = function(){
								var sub = autoOpt.Sub1;
								if ((typeof sub !== "undefined")&&(sub !== null)){
									com.cancelDualGrid(main.grid, sub.grid, main.cnlGridCb, sub.cnlGridCb);
								}
								else {
									com.cancelGrid(main.grid);
								}
							}
						}
						else if(i==gcm.BTN.SAVE.nm){
							item.cbFnc = function(){
								var sub = autoOpt.Sub1;
								if ((typeof sub !== "undefined")&&(sub !== null)){
									com.saveDualGrid(main.grid, sub.grid, null, main.savSbm, main.allSavSbm, sub.savSbm);
								}
								else if ((typeof main.savSbm !== "undefined")&&(main.savSbm !== null)){
									com.saveGrid(main.grid, main.savSbm);
								}
							}
						}
						else if(i==gcm.BTN.EXL_I.nm){
							item.cbFnc = function(){
								com.exlUploadGrid(main.grid, codeOptions);
							}
						}
						else if(i==gcm.BTN.EXL.nm){
							item.cbFnc = function(){
								com.exlGrid(main.grid);
							}
						}
						else if(i==gcm.BTN.EXL_F.nm){
							item.cbFnc = function(){
								com.exlFrmGrid(main.grid);
							}
						}
						else if(i==gcm.BTN.CLOSE.nm){
							item.cbFnc = function(){
								var sub = autoOpt.Sub1;
								if ((typeof sub !== "undefined")&&(sub !== null)){
									com.closeTab(main.grid, sub.grid);
								}
								else{
									com.closeTab(main.grid);
								}
							}
						}
					}
					else if( type == gcm.DISP_TYPE.DUAL_GRID2){ //듀얼 그리드 에서 서브그리드만 저장
						if(i == gcm.BTN.SEARCH.nm){
							item.cbFnc = function(){
								var sub = autoOpt.Sub1;
								if ((typeof sub !== "undefined")&&(sub !== null)){
									com.searchDualGrid(main.grid, sub.grid, null, main.srchSbm, main.savSbm, main.allSavSbm, sub.savSbm)
								}
								else{
									com.searchGrid(main.grid, main.srchSbm , main.savSbm);
								}
							}
						}
						else if(i == gcm.BTN.ADD.nm){
							item.cbFnc = function(){
								var sub = autoOpt.Sub1;
								if ((typeof sub.keySbm !== "undefined")&&(sub.keySbm !== null)){
									$p.executeSubmission(sub.keySbm);
								}
								else{
									com.addGrid2(sub.grid,sub.focusColumn);
								}
							}
						}
						else if(i == gcm.BTN.DEL.nm){
							item.cbFnc = function(){
								var sub = autoOpt.Sub1;
								if ((typeof sub !== "undefined")&&(sub !== null)){
									com.delGrid(sub.grid);
								}
							}
						}
						else if(i==gcm.BTN.CNL.nm){
							item.cbFnc = function(){
								var sub = autoOpt.Sub1;
								if ((typeof sub !== "undefined")&&(sub !== null)){
									com.cancelGrid(sub.grid, null, sub.cnlGridCb);
								}
							}
						}
						else if(i==gcm.BTN.SAVE.nm){
							item.cbFnc = function(){
								var sub = autoOpt.Sub1;
								if ((typeof sub !== "undefined")&&(sub !== null)){
									com.saveGrid(sub.grid, sub.savSbm);
								}
							}
						}
						else if(i==gcm.BTN.EXL_I.nm){
							item.cbFnc = function(){
								var sub = autoOpt.Sub1;
								if ((typeof sub !== "undefined")&&(sub !== null)){
									if ((typeof sub.exlGrid !== "undefined")&&(sub.exlGrid !== null)){
										com.exlUploadGrid(sub.exlGrid, codeOptions);
									}
									else {
										com.exlUploadGrid(sub.grid, codeOptions);
									}
								}
							}
						}
						else if(i==gcm.BTN.EXL.nm){ 
							
							item.cbFnc = function(){
								var sub = autoOpt.Sub1;
								if ((typeof sub !== "undefined")&&(sub !== null)){
									if ((typeof sub.exlGrid !== "undefined")&&(sub.exlGrid !== null)){
										com.exlGrid(sub.exlGrid, sub.grid);
									}
									else {
										com.exlGrid(sub.grid);
									}
								}
							}
						}
						else if(i==gcm.BTN.EXL_F.nm){
							item.cbFnc = function(){
								var sub = autoOpt.Sub1;
								if ((typeof sub !== "undefined")&&(sub !== null)){
									if ((typeof sub.exlGrid !== "undefined")&&(sub.exlGrid !== null)){
										com.exlFrmGrid(sub.exlGrid);
									}
									else {
										com.exlFrmGrid(sub.grid);
									}
								}
							}
						}
						else if(i==gcm.BTN.CLOSE.nm){
							item.cbFnc = function(){
								var sub = autoOpt.Sub1;
								if ((typeof sub !== "undefined")&&(sub !== null)){
									com.closeTab(main.grid, sub.grid);
								}
								else{
									com.closeTab(main.grid);
								}
							}
						}
					}
					else if( type == gcm.DISP_TYPE.DUAL_GRID3){ //듀얼 그리드
						if(i == gcm.BTN.SEARCH.nm){
							item.cbFnc = function(){
								var sub = autoOpt.Sub2;
								if ((typeof sub !== "undefined")&&(sub !== null)){
									com.searchDualGrid(main.grid, sub.grid, null, main.srchSbm, main.savSbm, main.allSavSbm, sub.savSbm)
								}
								else{
									com.searchGrid(main.grid, main.srchSbm , main.savSbm);
								}
							}
						}
						else if(i == gcm.BTN.ADD.nm){
							item.cbFnc = function(){
								var sub = autoOpt.Sub2;
								if ((typeof main.keySbm !== "undefined")&&(main.keySbm !== null)){
									$p.executeSubmission(sub.keySbm);
								}
								else{
									com.addGrid2(sub.grid,sub.focusColumn);
								}
							}
						}
						else if(i == gcm.BTN.DEL.nm){
							item.cbFnc = function(){
								var sub = autoOpt.Sub2;
								if ((typeof sub !== "undefined")&&(sub !== null)){
									com.delGrid(sub.grid);
								}
							}
						}
						else if(i==gcm.BTN.CNL.nm){
							item.cbFnc = function(){
								var sub = autoOpt.Sub2;
								if ((typeof sub !== "undefined")&&(sub !== null)){
									com.cancelGrid(sub.grid);
								}
							}
						}
						else if(i==gcm.BTN.SAVE.nm){
							item.cbFnc = function(){
								var sub = autoOpt.Sub2;
								if ((typeof sub !== "undefined")&&(sub !== null)){
									com.saveGrid(sub.grid, sub.savSbm);
								}
							}
						}
						else if(i==gcm.BTN.EXL_I.nm){
							item.cbFnc = function(){
								var sub = autoOpt.Sub2;
								com.exlUploadGrid(sub.grid, codeOptions);
							}
						}
						else if(i==gcm.BTN.EXL.nm){
							item.cbFnc = function(){
								var sub = autoOpt.Sub2;
								com.exlGrid(sub.grid);
							}
						}
						else if(i==gcm.BTN.EXL_F.nm){
							item.cbFnc = function(){
								var sub = autoOpt.Sub2;
								com.exlFrmGrid(sub.grid);
							}
						}
						else if(i==gcm.BTN.CLOSE.nm){
							item.cbFnc = function(){
								var sub = autoOpt.Sub2;
								if ((typeof sub !== "undefined")&&(sub !== null)){
									com.closeTab(main.grid, sub.grid);
								}
								else{
									com.closeTab(main.grid);
								}
							}
						}
					}
					else if( type == gcm.DISP_TYPE.DUAL_GRID_FORM){ //복함 그리드와 폼
						if(i == gcm.BTN.SEARCH.nm){
							item.cbFnc = function(){

								var sub = autoOpt.Sub1;
								if ((typeof sub !== "undefined")&&(sub !== null)){
									com.searchDualGrid(main.grid, sub.grid, main.frm, main.srchSbm, main.savSbm, main.allSavSbm, sub.savSbm)
								}
								else{
									com.searchGrid(main.grid, main.srchSbm , main.savSbm);
								}
							}
						}
						else if(i == gcm.BTN.ADD.nm){
							item.cbFnc = function(){
								if ((typeof main.keySbm !== "undefined")&&(main.keySbm !== null)){
									$p.executeSubmission(main.keySbm);
								}
								else {
									com.addGrid2(main.grid,main.focusColumn);
									
								}	
							} 
						}
						else if(i == gcm.BTN.DEL.nm){
							item.cbFnc = function(){
								var sub = autoOpt.Sub1;
								if ((typeof sub !== "undefined")&&(sub !== null)){
									com.delDualGrid(main.grid, sub.grid);
								}
								else {
									com.delGrid(main.grid);
								}
							}
						}
						else if(i==gcm.BTN.CNL.nm){
							item.cbFnc = function(){
								var sub = autoOpt.Sub1;
								if ((typeof sub !== "undefined")&&(sub !== null)){
									com.cancelDualGrid(main.grid, sub.grid);
								}
								else {
									com.cancelGrid(main.grid);
								}
							}
						}
						else if(i==gcm.BTN.SAVE.nm){
							item.cbFnc = function(){

								var sub = autoOpt.Sub1;
								if ((typeof sub !== "undefined")&&(sub !== null)){
									com.saveDualGrid(main.grid, sub.grid, main.frm, main.savSbm, main.allSavSbm, sub.savSbm);
								}
								else {
									com.saveGrid(main.grid, main.savSbm);
								}
							}
						}
						else if(i==gcm.BTN.EXL_I.nm){
							item.cbFnc = function(){
								com.exlUploadGrid(main.grid, codeOptions);
							}
						}
						else if(i==gcm.BTN.EXL.nm){
							item.cbFnc = function(){
								com.exlGrid(main.grid);
							}
						}
						else if(i==gcm.BTN.EXL_F.nm){
							item.cbFnc = function(){
								com.exlFrmGrid(main.grid);
							}
						}
						else if(i==gcm.BTN.CLOSE.nm){
							item.cbFnc = function(){
								var sub = autoOpt.Sub1;
								if ((typeof sub !== "undefined")&&(sub !== null)){
									com.closeTab(main.grid, sub.grid);
								}
								else{
									com.closeTab(main.grid);
								}
							}
						}
					}
					else if( type == gcm.DISP_TYPE.DUAL_GRID_FORM2){ //복함 그리드와 폼
						if(i == gcm.BTN.SEARCH.nm){
							item.cbFnc = function(){
								
								var sub = autoOpt.Sub1;
								if ((typeof sub !== "undefined")&&(sub !== null)){
									com.searchDualGrid(main.grid, sub.grid, main.frm, main.srchSbm, main.savSbm, main.allSavSbm, sub.savSbm)
								}
								else{
									com.searchGrid(sub.grid, sub.srchSbm , sub.savSbm);
								}
							}
						}
						else if(i == gcm.BTN.ADD.nm){
							var sub = autoOpt.Sub1;
							item.cbFnc = function(){
								if ((typeof sub.keySbm !== "undefined")&&(sub.keySbm !== null)){
									$p.executeSubmission(sub.keySbm);
								} 	else{
									com.addGrid2(sub.grid,sub.focusColumn);
								}
							}
						}
						else if(i == gcm.BTN.DEL.nm){
							item.cbFnc = function(){
								var sub = autoOpt.Sub1;
								if ((typeof sub !== "undefined")&&(sub !== null)){
									com.delGrid(sub.grid);									
								}
								else {
									com.delGrid(sub.grid);
								}
							}
						}
						else if(i==gcm.BTN.CNL.nm){
							item.cbFnc = function(){
								var sub = autoOpt.Sub1;
								if ((typeof sub !== "undefined")&&(sub !== null)){
									com.cancelDualGrid(main.grid, sub.grid);
								}
								else {
									com.cancelGrid(sub.grid);
								}
							}
						}
						else if(i==gcm.BTN.SAVE.nm){
							item.cbFnc = function(){
								
								var sub = autoOpt.Sub1;
								if ((typeof sub !== "undefined")&&(sub !== null)){
									com.saveDualGrid(main.grid, sub.grid, main.frm, main.savSbm, main.allSavSbm, sub.savSbm);
								}
								else {
									com.saveGrid(sub.grid, sub.savSbm);
								}
							}
						}
						else if(i==gcm.BTN.EXL_I.nm){
							item.cbFnc = function(){
								com.exlUploadGrid(sub.grid, codeOptions);
							}
						}
						else if(i==gcm.BTN.EXL.nm){
							item.cbFnc = function(){
								com.exlGrid(sub.grid);
							}
						}
						else if(i==gcm.BTN.EXL_F.nm){
							item.cbFnc = function(){
								com.exlFrmGrid(sub.grid);
							}
						}
						else if(i==gcm.BTN.CLOSE.nm){
							item.cbFnc = function(){
								var sub = autoOpt.Sub1;
								if ((typeof sub !== "undefined")&&(sub !== null)){
									com.closeTab(main.grid, sub.grid);
								}
								else{
									com.closeTab(sub.grid);
								}
							}
						}
					}
					
					else if( type == gcm.DISP_TYPE.THIRD_GRID_FORM){ //3 그리드와 폼
						if(i == gcm.BTN.SEARCH.nm){
							item.cbFnc = function(){
								
								var sub1 = autoOpt.Sub1;
								var sub2 = autoOpt.Sub2;
								if ((typeof sub1 !== "undefined")&&(sub1 !== null)&&(typeof sub2 !== "undefined")&&(sub2 !== null)){
									com.searchThirdGrid(main.grid, sub1.grid, sub1.grid, main.frm, main.srchSbm, main.savSbm,
											main.allSavSbm, sub1.savSbm, sub2.savSbm)
								}
								else{
									com.searchGrid(main.grid, main.srchSbm , main.savSbm);
								}
								
							}
						}
						else if(i == gcm.BTN.ADD.nm){
							item.cbFnc = function(){
								if ((typeof main.keySbm !== "undefined")&&(main.keySbm !== null)){
									$p.executeSubmission(main.keySbm);
								}
							}
						}
						else if(i == gcm.BTN.DEL.nm){
							item.cbFnc = function(){
								var sub1 = autoOpt.Sub1;
								var sub2 = autoOpt.Sub2;
								if ((typeof sub1 !== "undefined")&&(sub1 !== null)&&(typeof sub2 !== "undefined")&&(sub2 !== null)){
									com.delThirdGrid(main.grid, sub1.grid, sub2.grid);
								}
								else {
									com.delGrid(main.grid);
								}
							}
						}
						else if(i==gcm.BTN.CNL.nm){
							item.cbFnc = function(){
								var sub1 = autoOpt.Sub1;
								var sub2 = autoOpt.Sub2;
								if ((typeof sub1 !== "undefined")&&(sub1 !== null)&&(typeof sub2 !== "undefined")&&(sub2 !== null)){
									com.cancelThirdGrid(main.grid, sub1.grid, sub2.grid);
								}
								else {
									com.cancelGrid(main.grid);
								}
							}
						}
						else if(i==gcm.BTN.SAVE.nm){
							item.cbFnc = function(){
								var sub1 = autoOpt.Sub1;
								var sub2 = autoOpt.Sub2;
								if ((typeof sub1 !== "undefined")&&(sub1 !== null)&&(typeof sub2 !== "undefined")&&(sub2 !== null)){
									com.saveThirdGrid(main.grid, sub1.grid, sub2.grid, main.frm, main.savSbm, main.allSavSbm, sub1.savSbm, sub2.savSbm);
								}
								else {
									com.saveGrid(main.grid, main.savSbm);
								}
							}
						}
						else if(i==gcm.BTN.EXL_I.nm){
							item.cbFnc = function(){
								com.exlUploadGrid(main.grid, codeOptions);
							}
						}
						else if(i==gcm.BTN.EXL.nm){
							item.cbFnc = function(){
								com.exlGrid(main.grid);
							}
						}
						else if(i==gcm.BTN.EXL_F.nm){
							item.cbFnc = function(){
								com.exlFrmGrid(main.grid);
							}
						}
						else if(i==gcm.BTN.CLOSE.nm){
							item.cbFnc = function(){
								var sub = autoOpt.Sub1;
								if ((typeof sub !== "undefined")&&(sub !== null)){
									com.closeTab(main.grid, sub.grid);
								}
								else{
									com.closeTab(main.grid);
								}
							}
						}
					}
					
					if(i == gcm.BTN.SEARCH.nm){

						if ((typeof main.srchGrp !== "undefined")&&(main.srchGrp !== null)){
							com.setEnterKeyEvent(main.srchGrp, item.cbFnc);
						}
					}
					mainBtn.bind("onclick", item.cbFnc);
				}
			}
		} catch (e) {
			
		}
	}
	return gcm.BTN;
};

/*com.updateMainBtn2 = function(wfm_mainBtn,type, autoOpt, usrOpt, codeOptions) {
	var programAuthority = gcm.CUR_PROGRAM_AUTH;
	com.enableDisp(autoOpt);
	
	com.initGridInfo(autoOpt); //그리드 항목명 저장
	
	if(programAuthority.AUTH_CHECK != 'Y')return;
	
	var tmpParentIdx = 0;
	
	for(var i in gcm.BTN){
		try {
			var item = gcm.BTN[i];
			if(eval("programAuthority."+item.value) == "Y"){
				
				if ((typeof usrOpt !== "undefined")&&(usrOpt !== null)&&
						((typeof eval("usrOpt."+i) === "function")||(typeof usrOpt[i] !== "undefined"))) {
					var main = autoOpt.Main;
					if (typeof eval("usrOpt."+i) === "function") {
						var mainBtn = wfm_mainBtn.getWindow().btn_main_generator.getChild(tmpParentIdx++, "btn_main");
						var str = item.str;
						mainBtn.setValue(str);
						mainBtn.addClass(item.class);
						mainBtn.bind("onclick", eval("usrOpt."+i));
						if ((i == gcm.BTN.SEARCH.nm)&&(typeof main.srchGrp !== "undefined")&&(main.srchGrp !== null)){
							com.setEnterKeyEvent(main.srchGrp, eval("usrOpt."+i));
						}
					}
					else if((typeof eval(usrOpt[i].cbFnc) === "function")||(typeof usrOpt[i].nm !== "undefined")
							||(typeof usrOpt[i].class !== "undefined")){
						var mainBtn = wfm_mainBtn.getWindow().btn_main_generator.getChild(tmpParentIdx++, "btn_main");
						if(typeof eval(usrOpt[i].cbFnc) === "function"){
							mainBtn.bind("onclick", eval(usrOpt[i].cbFnc));
							if ((i == gcm.BTN.SEARCH.nm)&&(typeof main.srchGrp !== "undefined")&&(main.srchGrp !== null)){
								com.setEnterKeyEvent(main.srchGrp, eval(usrOpt[i].cbFnc));
							}
						}
						if(usrOpt[i].nm !== "undefined"){
							mainBtn.setValue(usrOpt[i].nm);
						}
						if(usrOpt[i].class !== "undefined"){
							mainBtn.addClass(usrOpt[i].class);
						}
					}
					else{
						
					}
				}
				else if((typeof autoOpt !== "undefined")&&(autoOpt !== null)){
					var mainBtn = wfm_mainBtn.getWindow().btn_main_generator.getChild(tmpParentIdx++, "btn_main");
					var str = item.str;
					mainBtn.setValue(str);
					mainBtn.addClass(item.class);
					mainBtn.bind("onclick", null);
					
					var main = autoOpt.Main;
				
					if( type == gcm.DISP_TYPE.SINGLE_GRID){ //단일 그리드
						if(i == gcm.BTN.SEARCH.nm){
							item.cbFnc = function(){
								com.searchGrid(main.grid, main.srchSbm , main.savSbm);
							}
						}
						else if(i == gcm.BTN.ADD.nm){
							item.cbFnc = function(){
								if ((typeof main.keySbm !== "undefined")&&(main.keySbm !== null)){
									$p.executeSubmission(main.keySbm);
								}
								else{
									com.addGrid2(main.grid,main.focusColumn);
								}
							}
						}
						else if(i == gcm.BTN.DEL.nm){
							item.cbFnc = function(){
								com.delGrid(main.grid);
							}
						}
						else if(i==gcm.BTN.CNL.nm){
							item.cbFnc = function(){
								com.cancelGrid(main.grid);
							}
						}
						else if(i==gcm.BTN.SAVE.nm){
							item.cbFnc = function(){
								com.saveGrid(main.grid, main.savSbm);
							}
						}
						else if(i==gcm.BTN.EXL_I.nm){
							item.cbFnc = function(){
								com.exlUploadGrid(main.grid, codeOptions);
							}
						}
						else if(i==gcm.BTN.EXL.nm){
							item.cbFnc = function(){
								
								com.exlGrid(main.grid);
							}
						}
						else if(i==gcm.BTN.EXL_F.nm){
							item.cbFnc = function(){
								
								com.exlFrmGrid(main.grid);
							}
						}
						else if(i==gcm.BTN.CLOSE.nm){
							item.cbFnc = function(){
								com.closeTab(main.grid);
							}
						}
					}
					else if( type == gcm.DISP_TYPE.SINGLE_GRID_FORM){ //단일 그리드와 폼
						if(i == gcm.BTN.SEARCH.nm){
							item.cbFnc = function(){
								com.searchGridForm(main.grid, null, main.srchSbm , main.savSbm);
							}
						}
						else if(i == gcm.BTN.ADD.nm){
							item.cbFnc = function(){
								if ((typeof main.keySbm !== "undefined")&&(main.keySbm !== null)){
									$p.executeSubmission(main.keySbm);
								}
								else{
									com.addGrid2(main.grid,main.focusColumn);
								}
							}
						}
						else if(i == gcm.BTN.DEL.nm){
							item.cbFnc = function(){
								com.delGrid(main.grid);
							}
						}
						else if(i==gcm.BTN.CNL.nm){
							item.cbFnc = function(){
								com.cancelGrid(main.grid);
							}
						}
						else if(i==gcm.BTN.SAVE.nm){
							item.cbFnc = function(){
								com.saveGridForm(main.grid, main.frm, main.savSbm);
							}
						}
						else if(i==gcm.BTN.EXL_I.nm){
							item.cbFnc = function(){
								com.exlUploadGrid(main.grid, codeOptions);
							}
						}
						else if(i==gcm.BTN.EXL.nm){
							item.cbFnc = function(){
								com.exlGrid(main.grid);
							}
						}
						else if(i==gcm.BTN.EXL_F.nm){
							item.cbFnc = function(){
								com.exlFrmGrid(main.grid);
							}
						}
						else if(i==gcm.BTN.CLOSE.nm){
							item.cbFnc = function(){
								com.closeTab(main.grid);
							}
						}
					}
					else if( type == gcm.DISP_TYPE.DUAL_GRID){ //듀얼 그리드
						if(i == gcm.BTN.SEARCH.nm){
							item.cbFnc = function(){
								var sub = autoOpt.Sub1;
								if ((typeof sub !== "undefined")&&(sub !== null)){
									com.searchDualGrid(main.grid, sub.grid, null, main.srchSbm, main.savSbm, main.allSavSbm, sub.savSbm)
								}
								else{
									com.searchGrid(main.grid, main.srchSbm , main.savSbm);
								}
							}
						}
						else if(i == gcm.BTN.ADD.nm){
							item.cbFnc = function(){
								if ((typeof main.keySbm !== "undefined")&&(main.keySbm !== null)){
									$p.executeSubmission(main.keySbm);
								}
								else{
									com.addGrid2(main.grid,main.focusColumn);
								}
							}
						}
						else if(i == gcm.BTN.DEL.nm){
							item.cbFnc = function(){
								var sub = autoOpt.Sub1;
								if ((typeof sub !== "undefined")&&(sub !== null)){
									com.delDualGrid(main.grid, sub.grid);
								}
								else {
									com.delGrid(main.grid);
								}
							}
						}
						else if(i==gcm.BTN.CNL.nm){
							item.cbFnc = function(){
								var sub = autoOpt.Sub1;
								if ((typeof sub !== "undefined")&&(sub !== null)){
									com.cancelDualGrid(main.grid, sub.grid, main.cnlGridCb, sub.cnlGridCb);
								}
								else {
									com.cancelGrid(main.grid);
								}
							}
						}
						else if(i==gcm.BTN.SAVE.nm){
							item.cbFnc = function(){
								var sub = autoOpt.Sub1;
								if ((typeof sub !== "undefined")&&(sub !== null)){
									com.saveDualGrid(main.grid, sub.grid, null, main.savSbm, main.allSavSbm, sub.savSbm);
								}
								else if ((typeof main.savSbm !== "undefined")&&(main.savSbm !== null)){
									com.saveGrid(main.grid, main.savSbm);
								}
							}
						}
						else if(i==gcm.BTN.EXL_I.nm){
							item.cbFnc = function(){
								com.exlUploadGrid(main.grid, codeOptions);
							}
						}
						else if(i==gcm.BTN.EXL.nm){
							item.cbFnc = function(){
								com.exlGrid(main.grid);
							}
						}
						else if(i==gcm.BTN.EXL_F.nm){
							item.cbFnc = function(){
								com.exlFrmGrid(main.grid);
							}
						}
						else if(i==gcm.BTN.CLOSE.nm){
							item.cbFnc = function(){
								var sub = autoOpt.Sub1;
								if ((typeof sub !== "undefined")&&(sub !== null)){
									com.closeTab(main.grid, sub.grid);
								}
								else{
									com.closeTab(main.grid);
								}
							}
						}
					}
					else if( type == gcm.DISP_TYPE.DUAL_GRID2){ //듀얼 그리드 에서 서브그리드만 저장
						if(i == gcm.BTN.SEARCH.nm){
							item.cbFnc = function(){
								var sub = autoOpt.Sub1;
								if ((typeof sub !== "undefined")&&(sub !== null)){
									com.searchDualGrid(main.grid, sub.grid, null, main.srchSbm, main.savSbm, main.allSavSbm, sub.savSbm)
								}
								else{
									com.searchGrid(main.grid, main.srchSbm , main.savSbm);
								}
							}
						}
						else if(i == gcm.BTN.ADD.nm){
							item.cbFnc = function(){
								var sub = autoOpt.Sub1;
								if ((typeof sub.keySbm !== "undefined")&&(sub.keySbm !== null)){
									$p.executeSubmission(sub.keySbm);
								}
								else{
									com.addGrid2(sub.grid,sub.focusColumn);
								}
							}
						}
						else if(i == gcm.BTN.DEL.nm){
							item.cbFnc = function(){
								var sub = autoOpt.Sub1;
								if ((typeof sub !== "undefined")&&(sub !== null)){
									com.delGrid(sub.grid);
								}
							}
						}
						else if(i==gcm.BTN.CNL.nm){
							item.cbFnc = function(){
								var sub = autoOpt.Sub1;
								if ((typeof sub !== "undefined")&&(sub !== null)){
									com.cancelGrid(sub.grid, null, sub.cnlGridCb);
								}
							}
						}
						else if(i==gcm.BTN.SAVE.nm){
							item.cbFnc = function(){
								var sub = autoOpt.Sub1;
								if ((typeof sub !== "undefined")&&(sub !== null)){
									com.saveGrid(sub.grid, sub.savSbm);
								}
							}
						}
						else if(i==gcm.BTN.EXL_I.nm){
							item.cbFnc = function(){
								var sub = autoOpt.Sub1;
								if ((typeof sub !== "undefined")&&(sub !== null)){
									if ((typeof sub.exlGrid !== "undefined")&&(sub.exlGrid !== null)){
										com.exlUploadGrid(sub.exlGrid, codeOptions);
									}
									else {
										com.exlUploadGrid(sub.grid, codeOptions);
									}
								}
							}
						}
						else if(i==gcm.BTN.EXL.nm){
							item.cbFnc = function(){
								var sub = autoOpt.Sub1;
								if ((typeof sub !== "undefined")&&(sub !== null)){
									if ((typeof sub.exlGrid !== "undefined")&&(sub.exlGrid !== null)){
										com.exlGrid(sub.exlGrid);
									}
									else {
										com.exlGrid(sub.grid);
									}
								}
							}
						}
						else if(i==gcm.BTN.EXL_F.nm){
							item.cbFnc = function(){
								var sub = autoOpt.Sub1;
								if ((typeof sub !== "undefined")&&(sub !== null)){
									if ((typeof sub.exlGrid !== "undefined")&&(sub.exlGrid !== null)){
										com.exlFrmGrid(sub.exlGrid);
									}
									else {
										com.exlFrmGrid(sub.grid);
									}
								}
							}
						}
						else if(i==gcm.BTN.CLOSE.nm){
							item.cbFnc = function(){
								var sub = autoOpt.Sub1;
								if ((typeof sub !== "undefined")&&(sub !== null)){
									com.closeTab(main.grid, sub.grid);
								}
								else{
									com.closeTab(main.grid);
								}
							}
						}
					}
					else if( type == gcm.DISP_TYPE.DUAL_GRID3){ //듀얼 그리드
						if(i == gcm.BTN.SEARCH.nm){
							item.cbFnc = function(){
								var sub = autoOpt.Sub2;
								if ((typeof sub !== "undefined")&&(sub !== null)){
									com.searchDualGrid(main.grid, sub.grid, null, main.srchSbm, main.savSbm, main.allSavSbm, sub.savSbm)
								}
								else{
									com.searchGrid(main.grid, main.srchSbm , main.savSbm);
								}
							}
						}
						else if(i == gcm.BTN.ADD.nm){
							item.cbFnc = function(){
								var sub = autoOpt.Sub2;
								if ((typeof main.keySbm !== "undefined")&&(main.keySbm !== null)){
									$p.executeSubmission(sub.keySbm);
								}
								else{
									com.addGrid2(sub.grid,sub.focusColumn);
								}
							}
						}
						else if(i == gcm.BTN.DEL.nm){
							item.cbFnc = function(){
								var sub = autoOpt.Sub2;
								if ((typeof sub !== "undefined")&&(sub !== null)){
									com.delGrid(sub.grid);
								}
							}
						}
						else if(i==gcm.BTN.CNL.nm){
							item.cbFnc = function(){
								var sub = autoOpt.Sub2;
								if ((typeof sub !== "undefined")&&(sub !== null)){
									com.cancelGrid(sub.grid);
								}
							}
						}
						else if(i==gcm.BTN.SAVE.nm){
							item.cbFnc = function(){
								var sub = autoOpt.Sub2;
								if ((typeof sub !== "undefined")&&(sub !== null)){
									com.saveGrid(sub.grid, sub.savSbm);
								}
							}
						}
						else if(i==gcm.BTN.EXL_I.nm){
							item.cbFnc = function(){
								var sub = autoOpt.Sub2;
								com.exlUploadGrid(sub.grid, codeOptions);
							}
						}
						else if(i==gcm.BTN.EXL.nm){
							item.cbFnc = function(){
								var sub = autoOpt.Sub2;
								com.exlGrid(sub.grid);
							}
						}
						else if(i==gcm.BTN.EXL_F.nm){
							item.cbFnc = function(){
								var sub = autoOpt.Sub2;
								com.exlFrmGrid(sub.grid);
							}
						}
						else if(i==gcm.BTN.CLOSE.nm){
							item.cbFnc = function(){
								var sub = autoOpt.Sub2;
								if ((typeof sub !== "undefined")&&(sub !== null)){
									com.closeTab(main.grid, sub.grid);
								}
								else{
									com.closeTab(main.grid);
								}
							}
						}
					}
					else if( type == gcm.DISP_TYPE.DUAL_GRID_FORM){ //복함 그리드와 폼
						if(i == gcm.BTN.SEARCH.nm){
							item.cbFnc = function(){

								var sub = autoOpt.Sub1;
								if ((typeof sub !== "undefined")&&(sub !== null)){
									com.searchDualGrid(main.grid, sub.grid, main.frm, main.srchSbm, main.savSbm, main.allSavSbm, sub.savSbm)
								}
								else{
									com.searchGrid(main.grid, main.srchSbm , main.savSbm);
								}
							}
						}
						else if(i == gcm.BTN.ADD.nm){
							item.cbFnc = function(){
								if ((typeof main.keySbm !== "undefined")&&(main.keySbm !== null)){
									$p.executeSubmission(main.keySbm);
								}
								else {
									com.addGrid2(main.grid,main.focusColumn);
									
								}	
							} 
						}
						else if(i == gcm.BTN.DEL.nm){
							item.cbFnc = function(){
								var sub = autoOpt.Sub1;
								if ((typeof sub !== "undefined")&&(sub !== null)){
									com.delDualGrid(main.grid, sub.grid);
								}
								else {
									com.delGrid(main.grid);
								}
							}
						}
						else if(i==gcm.BTN.CNL.nm){
							item.cbFnc = function(){
								var sub = autoOpt.Sub1;
								if ((typeof sub !== "undefined")&&(sub !== null)){
									com.cancelDualGrid(main.grid, sub.grid);
								}
								else {
									com.cancelGrid(main.grid);
								}
							}
						}
						else if(i==gcm.BTN.SAVE.nm){
							item.cbFnc = function(){

								var sub = autoOpt.Sub1;
								if ((typeof sub !== "undefined")&&(sub !== null)){
									com.saveDualGrid(main.grid, sub.grid, main.frm, main.savSbm, main.allSavSbm, sub.savSbm);
								}
								else {
									com.saveGrid(main.grid, main.savSbm);
								}
							}
						}
						else if(i==gcm.BTN.EXL_I.nm){
							item.cbFnc = function(){
								com.exlUploadGrid(main.grid, codeOptions);
							}
						}
						else if(i==gcm.BTN.EXL.nm){
							item.cbFnc = function(){
								com.exlGrid(main.grid);
							}
						}
						else if(i==gcm.BTN.EXL_F.nm){
							item.cbFnc = function(){
								com.exlFrmGrid(main.grid);
							}
						}
						else if(i==gcm.BTN.CLOSE.nm){
							item.cbFnc = function(){
								var sub = autoOpt.Sub1;
								if ((typeof sub !== "undefined")&&(sub !== null)){
									com.closeTab(main.grid, sub.grid);
								}
								else{
									com.closeTab(main.grid);
								}
							}
						}
					}
					else if( type == gcm.DISP_TYPE.DUAL_GRID_FORM2){ //복함 그리드와 폼
						if(i == gcm.BTN.SEARCH.nm){
							item.cbFnc = function(){
								
								var sub = autoOpt.Sub1;
								if ((typeof sub !== "undefined")&&(sub !== null)){
									com.searchDualGrid(main.grid, sub.grid, main.frm, main.srchSbm, main.savSbm, main.allSavSbm, sub.savSbm)
								}
								else{
									com.searchGrid(sub.grid, sub.srchSbm , sub.savSbm);
								}
							}
						}
						else if(i == gcm.BTN.ADD.nm){
							var sub = autoOpt.Sub1;
							item.cbFnc = function(){
								if ((typeof sub.keySbm !== "undefined")&&(sub.keySbm !== null)){
									$p.executeSubmission(sub.keySbm);
								} 	else{
									com.addGrid2(sub.grid,sub.focusColumn);
								}
							}
						}
						else if(i == gcm.BTN.DEL.nm){
							item.cbFnc = function(){
								var sub = autoOpt.Sub1;
								if ((typeof sub !== "undefined")&&(sub !== null)){
									com.delGrid(sub.grid);									
								}
								else {
									com.delGrid(sub.grid);
								}
							}
						}
						else if(i==gcm.BTN.CNL.nm){
							item.cbFnc = function(){
								var sub = autoOpt.Sub1;
								if ((typeof sub !== "undefined")&&(sub !== null)){
									com.cancelDualGrid(main.grid, sub.grid);
								}
								else {
									com.cancelGrid(sub.grid);
								}
							}
						}
						else if(i==gcm.BTN.SAVE.nm){
							item.cbFnc = function(){
								
								var sub = autoOpt.Sub1;
								if ((typeof sub !== "undefined")&&(sub !== null)){
									com.saveDualGrid(main.grid, sub.grid, main.frm, main.savSbm, main.allSavSbm, sub.savSbm);
								}
								else {
									com.saveGrid(sub.grid, sub.savSbm);
								}
							}
						}
						else if(i==gcm.BTN.EXL_I.nm){
							item.cbFnc = function(){
								com.exlUploadGrid(sub.grid, codeOptions);
							}
						}
						else if(i==gcm.BTN.EXL.nm){
							item.cbFnc = function(){
								com.exlGrid(sub.grid);
							}
						}
						else if(i==gcm.BTN.EXL_F.nm){
							item.cbFnc = function(){
								com.exlFrmGrid(sub.grid);
							}
						}
						else if(i==gcm.BTN.CLOSE.nm){
							item.cbFnc = function(){
								var sub = autoOpt.Sub1;
								if ((typeof sub !== "undefined")&&(sub !== null)){
									com.closeTab(main.grid, sub.grid);
								}
								else{
									com.closeTab(sub.grid);
								}
							}
						}
					}
					
					else if( type == gcm.DISP_TYPE.THIRD_GRID_FORM){ //3 그리드와 폼
						if(i == gcm.BTN.SEARCH.nm){
							item.cbFnc = function(){
								
								var sub1 = autoOpt.Sub1;
								var sub2 = autoOpt.Sub2;
								if ((typeof sub1 !== "undefined")&&(sub1 !== null)&&(typeof sub2 !== "undefined")&&(sub2 !== null)){
									com.searchThirdGrid(main.grid, sub1.grid, sub1.grid, main.frm, main.srchSbm, main.savSbm,
											main.allSavSbm, sub1.savSbm, sub2.savSbm)
								}
								else{
									com.searchGrid(main.grid, main.srchSbm , main.savSbm);
								}
								
							}
						}
						else if(i == gcm.BTN.ADD.nm){
							item.cbFnc = function(){
								if ((typeof main.keySbm !== "undefined")&&(main.keySbm !== null)){
									$p.executeSubmission(main.keySbm);
								}
							}
						}
						else if(i == gcm.BTN.DEL.nm){
							item.cbFnc = function(){
								var sub1 = autoOpt.Sub1;
								var sub2 = autoOpt.Sub2;
								if ((typeof sub1 !== "undefined")&&(sub1 !== null)&&(typeof sub2 !== "undefined")&&(sub2 !== null)){
									com.delThirdGrid(main.grid, sub1.grid, sub2.grid);
								}
								else {
									com.delGrid(main.grid);
								}
							}
						}
						else if(i==gcm.BTN.CNL.nm){
							item.cbFnc = function(){
								var sub1 = autoOpt.Sub1;
								var sub2 = autoOpt.Sub2;
								if ((typeof sub1 !== "undefined")&&(sub1 !== null)&&(typeof sub2 !== "undefined")&&(sub2 !== null)){
									com.cancelThirdGrid(main.grid, sub1.grid, sub2.grid);
								}
								else {
									com.cancelGrid(main.grid);
								}
							}
						}
						else if(i==gcm.BTN.SAVE.nm){
							item.cbFnc = function(){
								var sub1 = autoOpt.Sub1;
								var sub2 = autoOpt.Sub2;
								if ((typeof sub1 !== "undefined")&&(sub1 !== null)&&(typeof sub2 !== "undefined")&&(sub2 !== null)){
									com.saveThirdGrid(main.grid, sub1.grid, sub2.grid, main.frm, main.savSbm, main.allSavSbm, sub1.savSbm, sub2.savSbm);
								}
								else {
									com.saveGrid(main.grid, main.savSbm);
								}
							}
						}
						else if(i==gcm.BTN.EXL_I.nm){
							item.cbFnc = function(){
								com.exlUploadGrid(main.grid, codeOptions);
							}
						}
						else if(i==gcm.BTN.EXL.nm){
							item.cbFnc = function(){
								com.exlGrid(main.grid);
							}
						}
						else if(i==gcm.BTN.EXL_F.nm){
							item.cbFnc = function(){
								com.exlFrmGrid(main.grid);
							}
						}
						else if(i==gcm.BTN.CLOSE.nm){
							item.cbFnc = function(){
								var sub = autoOpt.Sub1;
								if ((typeof sub !== "undefined")&&(sub !== null)){
									com.closeTab(main.grid, sub.grid);
								}
								else{
									com.closeTab(main.grid);
								}
							}
						}
					}
					
					if(i == gcm.BTN.SEARCH.nm){

						if ((typeof main.srchGrp !== "undefined")&&(main.srchGrp !== null)){
							com.setEnterKeyEvent(main.srchGrp, item.cbFnc);
						}
					}
					mainBtn.bind("onclick", item.cbFnc);
				}
			}
		} catch (e) {
			
		}
	}
	return gcm.BTN;
};*/

/**권한에 따른 메인 버튼 생성
 * 
 * @date 2021.03.13
 * @memberOf com
 * @author tracom
 * @param {} wfm_mainBtn main 버튼 window
 * @param {} btnOptions 버튼 옵션
 * 
사용 예
var btnOptions = 
[
	{userStr:"조회", auth: gcm.BTN.SEARCH, cbFnc: scwin.btn_search_onclick},
	{userStr:"추가", auth: gcm.BTN.ADD , cbFnc: scwin.btn_add_onclick},
	{userStr:"삭제", auth: gcm.BTN.DEL, cbFnc: scwin.btn_del_onclick},
	{userStr:"취소", auth: gcm.BTN.CNL, cbFnc: scwin.btn_cancel_onclick},
	{userStr:"저장", auth: gcm.BTN.SAVE, cbFnc: scwin.btn_save_onclick},
	{userStr:"엑셀", auth: gcm.BTN.EXL, cbFnc: scwin.btn_excel_onclick},
	{userStr:"엑셀업로드", auth: gcm.BTN.EXL_I, cbFnc: scwin.btn_uploadExcel_onclick},
	{userStr:"엑셀양식", auth: gcm.BTN.EXL_F, cbFnc: scwin.btn_excelForm_onclick},
	{userStr:"닫기", auth: gcm.BTN.CLOSE, cbFnc: scwin.btn_close_onclick}
];
com.setMainBtn3(btnOptions, wfm_mainBtn);
*/
com.setMainBtn3 = function(wfm_mainBtn, btnOptions) {
	var programAuthority = gcm.CUR_PROGRAM_AUTH;
	
	if(programAuthority.AUTH_CHECK != 'Y')return;
	
	for(var i=0; i<btnOptions.length; i++){
		try {
			var item = btnOptions[i];
			if(eval("programAuthority."+item.auth.value) == "Y"){
				var tmpParentIdx = wfm_mainBtn.getWindow().btn_main_generator.insertChild();
				var mainBtn = wfm_mainBtn.getWindow().btn_main_generator.getChild(tmpParentIdx, "btn_main");
				var str = "";
				if(item.userStr !== "undefined" && item.userStr!="" && item.userStr != null){
					str = item.userStr;
				}
				else{
					str = item.auth.str;
				}
					mainBtn.setValue(str);
				mainBtn.addClass(item.auth.class);
				if (typeof item.cbFnc === "function") {
					mainBtn.bind("onclick", item.cbFnc); 
				}
			}
		} catch (e) {
			
		}
	}
}

/**권한에 따른 팝업 버튼 생성(자동, 팝업 옵션에 따라 버튼 생성)
* 
* @date 2021.03.13
* @memberOf com
* @author tracom
* @param {} wfm_mainBtn main 버튼 window
* @param {} popOpt 팝업 옵션
* @param {} autoOpt 화면 자동 옵션
*/
com.setPopupBtn = function(wfm_mainBtn, popOpt, autoOpt) {
	var programAuthority = gcm.CUR_PROGRAM_AUTH;

	if(programAuthority.AUTH_CHECK != 'Y')return;

	for(var i in popOpt){
		
		try {
			var item = gcm.BTN[i];
			if(eval("programAuthority."+item.value) == "Y"){
				if ((typeof eval("popOpt."+i) === "function")||(popOpt[i].length>0)||(typeof eval(popOpt[i].cbFnc) === "function")) {
					if (typeof eval("popOpt."+i) === "function") {
						var tmpParentIdx = wfm_mainBtn.getWindow().btn_main_generator.insertChild();
						var mainBtn = wfm_mainBtn.getWindow().btn_main_generator.getChild(tmpParentIdx, "btn_main");
						var str = item.str;
						mainBtn.setValue(str);
						mainBtn.addClass(item.class);
						mainBtn.bind("onclick", eval("popOpt."+i));
					}
					else if(popOpt[i].length>0){
						for(var j=0; j<popOpt[i].length; j++){
							var tmpParentIdx = wfm_mainBtn.getWindow().btn_main_generator.insertChild();
							var mainBtn = wfm_mainBtn.getWindow().btn_main_generator.getChild(tmpParentIdx, "btn_main");
							var optArr = popOpt[i];
							if(typeof eval(optArr[j].cbFnc) === "function"){
								mainBtn.bind("onclick", eval(optArr[j].cbFnc));
							}
							if(optArr[j].nm !== "undefined"){
								mainBtn.setValue(optArr[j].nm);
							}
							if(optArr[j].class !== "undefined"){
								mainBtn.addClass(optArr[j].class);
							}
						}
					}
					else {
						var tmpParentIdx = wfm_mainBtn.getWindow().btn_main_generator.insertChild();
						var mainBtn = wfm_mainBtn.getWindow().btn_main_generator.getChild(tmpParentIdx, "btn_main");
						if(typeof eval(popOpt[i].cbFnc) === "function"){
							mainBtn.bind("onclick", eval(popOpt[i].cbFnc));
						}
						if(popOpt[i].nm !== "undefined"){
							mainBtn.setValue(popOpt[i].nm);
						}
						if(popOpt[i].class !== "undefined"){
							mainBtn.addClass(popOpt[i].class);
						}
					}
				}
				else{
					var tmpParentIdx = wfm_mainBtn.getWindow().btn_main_generator.insertChild();
					var mainBtn = wfm_mainBtn.getWindow().btn_main_generator.getChild(tmpParentIdx, "btn_main");
					var str = item.str;
					mainBtn.setValue(str);
					mainBtn.addClass(item.class);
					
					var main = autoOpt.Main;
					if(i == gcm.BTN.SEARCH.nm){
						item.cbFnc = function(){
							com.searchGrid(main.grid, main.srchSbm , main.savSbm);
						}
					}
					else if(i == gcm.BTN.ADD.nm){
						
						item.cbFnc = function(){
							if ((typeof main.keySbm !== "undefined")&&(main.keySbm !== null)) {
								$p.executeSubmission(main.keySbm);
							}
							else{
								com.addGrid2(main.grid,main.focusColumn);
							}
						}
					}
					else if(i == gcm.BTN.DEL.nm){
						item.cbFnc = function(){
							var yn_str = "";
							if ((typeof main.name !== "undefined")&&(main.name !== null)) {
								yn_str = "건의 " + main.name +" 삭제 하시겠습니까?";
							}
							com.delGrid(main.grid, yn_str);
						}
					}
					else if(i==gcm.BTN.CNL.nm){
						item.cbFnc = function(){
							var yn_str = "";
							if ((typeof main.name !== "undefined")&&(main.name !== null)) {
								yn_str = "건의 " + main.name +"에 저장하지 않은 데이터가 있습니다. 취소 하시겠습니까?";
							}
							com.cancelGrid(main.grid, yn_str);
						}
					}
					else if(i==gcm.BTN.SAVE.nm){
						item.cbFnc = function(){
							
							var yn_str = "";
							if ((typeof main.name !== "undefined")&&(main.name !== null)) {
								yn_str = "건의 " + main.name +" 데이터를 저장 하시겠습니까?";
							}
							com.saveGrid(main.grid, main.savSbm,yn_str);
						}
					}
					else if(i==gcm.BTN.EXL_I.nm){
						item.cbFnc = function(){
							com.exlUploadGrid(main.grid, codeOptions);
						}
					}
					else if(i==gcm.BTN.EXL.nm){
						item.cbFnc = function(){
							com.exlGrid(main.grid);
						}
					}
					else if(i==gcm.BTN.EXL_F.nm){
						item.cbFnc = function(){
							com.exlFrmGrid(main.grid);
						}
					}
					mainBtn.bind("onclick", item.cbFnc);
				}
			}
		} catch (e) {
			
		}
	}
	return gcm.BTN;
}

/**권한에 따른 서브 버튼 생성
 * 
 * @date 2021.03.13
 * @memberOf com
 * @author tracom
 * @param {Object} btnOptions 버튼 정보
 * @param {Object} generator 버튼 정보를 화면에 출력할 generator window
*/
com.setSubBtn = function(btnOptions, generator) {
	var programAuthority = gcm.CUR_PROGRAM_AUTH;
	
	if(programAuthority.AUTH_CHECK != 'Y')return;

	for(var i=0; i<btnOptions.length; i++){
		try {
			var subItem = btnOptions[i];
			if(eval("programAuthority."+subItem.auth.value) == "Y"){
				var tmpParentIdx = generator.insertChild();
				var subBtn = generator.getChild(tmpParentIdx, "btn_sub");
				var str = "";
				if(subItem.userStr !== "undefined" && subItem.userStr!="" && subItem.userStr != null){
					str = subItem.userStr;
				}
				else{
					str = subItem.auth.str;
				}
				subBtn.setValue(str);
				subBtn.addClass(subItem.auth.class);
				if (typeof subItem.cbFnc === "function") {
					subBtn.bind("onclick", subItem.cbFnc); 
				}
			}
		} catch (e) {
		
		}
	}
}

/**권한에 따른 서브 버튼 생성(자동, 서브 옵션에 따라 버튼 생성)
 * 
 * @date 2021.03.13
 * @memberOf com
 * @author tracom
 * @param {} wfm_subBtn 서브 버튼 window
 * @param {} autoOpt 화면 자동 옵션
 * @param {} subOpt 서브 옵션
 * 
 */
com.setSubBtn2 = function(wfm_subBtn,autoOpt, subOpt, codeOptions) {
	
	var programAuthority = gcm.CUR_PROGRAM_AUTH;

	if(programAuthority.AUTH_CHECK != 'Y')return;

	for(var i in subOpt){
		
		try {
			var item = gcm.BTN[i];
			if(eval("programAuthority."+item.value) == "Y"){
				if ((typeof eval("subOpt."+i) === "function")||(subOpt[i].length>0)||(typeof eval(subOpt[i].cbFnc) === "function")) {
					var sub = autoOpt.Sub1;
					if (typeof eval("subOpt."+i) === "function") {
						var tmpParentIdx = wfm_subBtn.getWindow().btn_sub_generator.insertChild();
						var subBtn = wfm_subBtn.getWindow().btn_sub_generator.getChild(tmpParentIdx, "btn_sub");
						var str = item.str;
						subBtn.setValue(str);
						subBtn.addClass(item.class);
						subBtn.bind("onclick", eval("subOpt."+i));
						
						if ((i == gcm.BTN.SEARCH.nm)&&(typeof sub.srchGrp !== "undefined")&&(sub.srchGrp !== null)){
							com.setEnterKeyEvent(sub.srchGrp, eval("subOpt."+i));
						}
					}
					else if(subOpt[i].length>0){
						for(var j=0; j<subOpt[i].length; j++){
							var tmpParentIdx = wfm_subBtn.getWindow().btn_sub_generator.insertChild();
							var subBtn = wfm_subBtn.getWindow().btn_sub_generator.getChild(tmpParentIdx, "btn_sub");
							var optArr = subOpt[i];
							if(typeof eval(optArr[j].cbFnc) === "function"){
								subBtn.bind("onclick", eval(optArr[j].cbFnc));
								if ((i == gcm.BTN.SEARCH.nm)&&(typeof sub.srchGrp !== "undefined")&&(sub.srchGrp !== null)){
									com.setEnterKeyEvent(sub.srchGrp, eval(optArr[j].cbFnc));
								}
							}
							if(optArr[j].nm !== "undefined"){
								subBtn.setValue(optArr[j].nm);
							}
							if(optArr[j].class !== "undefined"){
								subBtn.addClass(optArr[j].class);
							}
						}
					}
					else {
						var tmpParentIdx = wfm_subBtn.getWindow().btn_sub_generator.insertChild();
						var subBtn = wfm_subBtn.getWindow().btn_sub_generator.getChild(tmpParentIdx, "btn_sub");
						if(typeof eval(subOpt[i].cbFnc) === "function"){
							subBtn.bind("onclick", eval(subOpt[i].cbFnc));
							if ((i == gcm.BTN.SEARCH.nm)&&(typeof sub.srchGrp !== "undefined")&&(sub.srchGrp !== null)){
								com.setEnterKeyEvent(sub.srchGrp, eval(subOpt[i].cbFnc));
							}
						}
						if(subOpt[i].nm !== "undefined"){
							subBtn.setValue(subOpt[i].nm);
						}
						if(subOpt[i].class !== "undefined"){
							subBtn.addClass(subOpt[i].class);
						}
					}
				}
				else{
					var tmpParentIdx = wfm_subBtn.getWindow().btn_sub_generator.insertChild();
					var subBtn = wfm_subBtn.getWindow().btn_sub_generator.getChild(tmpParentIdx, "btn_sub");
					var str = item.str;
					subBtn.setValue(str);
					subBtn.addClass(item.class);
					
					var sub = autoOpt.Sub1;
					var main = autoOpt.Main;
					if(i == gcm.BTN.SEARCH.nm){
						item.cbFnc = function(){
							com.searchGrid(sub.grid, sub.srchSbm , sub.savSbm);
						}
					}
					else if(i == gcm.BTN.ADD.nm){
						
						item.cbFnc = function(){

							var focusRowIdx = main.grid.getFocusedRowIndex();
							
							if (focusRowIdx == -1) {
								if ((typeof main.name !== "undefined")&&(main.name !== null)){
									com.alert(main.name+" 선택 후에 추가 하실 수 있습니다.");
								}
								else{
									com.alert("부모 선택 후에 추가 하실 수 있습니다.");
								}
								return;
							} else {
								var rowStatus = main.grid.getRowStatus(focusRowIdx);
								if (rowStatus == "C") {
									if ((typeof main.name !== "undefined")&&(main.name !== null)){
										com.alert(main.name+" 등록 후에 추가 가능합니다.");
									}
									else{
										com.alert("부모 등록 후에 추가 가능합니다.");
									}
									return;
								}
							}
	
							if ((typeof sub.keySbm !== "undefined")&&(sub.keySbm !== null)) {
								$p.executeSubmission(sub.keySbm);
							}
							else{
								com.addGrid2(sub.grid,sub.focusColumn);
							}
						}
					}
					else if(i == gcm.BTN.DEL.nm){
						item.cbFnc = function(){
							var yn_str = "";
							if ((typeof sub.name !== "undefined")&&(sub.name !== null)) {
								yn_str = "건의 " + sub.name +" 삭제 하시겠습니까?";
							}
							com.delGrid(sub.grid, yn_str);
						}
					}
					else if(i==gcm.BTN.CNL.nm){
						item.cbFnc = function(){
							var yn_str = "";
							if ((typeof sub.name !== "undefined")&&(sub.name !== null)) {
								yn_str = "건의 " + sub.name +"에 저장하지 않은 데이터가 있습니다. 취소 하시겠습니까?";
							}
							com.cancelGrid(sub.grid, yn_str, sub.cnlGridCb);
						}
					}
					else if(i==gcm.BTN.SAVE.nm){
						item.cbFnc = function(){
							var yn_str = "";
							if ((typeof sub.name !== "undefined")&&(sub.name !== null)) {
								yn_str = "건의 " + sub.name +" 데이터를 저장 하시겠습니까?";
							}
							com.saveGrid(sub.grid, sub.savSbm,yn_str);
						}
					}
					else if(i==gcm.BTN.EXL_I.nm){
						item.cbFnc = function(){
							com.exlUploadGrid(sub.grid, codeOptions);
						}
					}
					else if(i==gcm.BTN.EXL.nm){
						item.cbFnc = function(){
							com.exlGrid(sub.grid);
						}
					}
					else if(i==gcm.BTN.EXL_F.nm){
						item.cbFnc = function(){
							com.exlFrmGrid(sub.grid);
						}
					}
					if(i == gcm.BTN.SEARCH.nm){

						if ((typeof sub.srchGrp !== "undefined")&&(sub.srchGrp !== null)){
							com.setEnterKeyEvent(sub.srchGrp, item.cbFnc);
						}
					}
					subBtn.bind("onclick", item.cbFnc);
				}
			}
		} catch (e) {
			
		}
	}
	return gcm.BTN;
};

/**권한에 따른 서브 버튼 생성
 * 
 * @date 2021.03.13
 * @memberOf com
 * @author tracom
 * @param {} btnOptions 버튼 옵션
 * @param {} wfm_subBtn sub 버튼 window
 * 
사용 예
var btnOptions = 
[
	{userStr:"조회", auth: gcm.BTN.SEARCH, cbFnc: scwin.btn_search_onclick},
	{userStr:"추가", auth: gcm.BTN.ADD , cbFnc: scwin.btn_add_onclick},
	{userStr:"삭제", auth: gcm.BTN.DEL, cbFnc: scwin.btn_del_onclick},
	{userStr:"취소", auth: gcm.BTN.CNL, cbFnc: scwin.btn_cancel_onclick},
	{userStr:"저장", auth: gcm.BTN.SAVE, cbFnc: scwin.btn_save_onclick},
	{userStr:"엑셀", auth: gcm.BTN.EXL, cbFnc: scwin.btn_excel_onclick},
	{userStr:"엑셀업로드", auth: gcm.BTN.EXL_I, cbFnc: scwin.btn_uploadExcel_onclick},
	{userStr:"엑셀양식", auth: gcm.BTN.EXL_F, cbFnc: scwin.btn_excelForm_onclick},
	{userStr:"닫기", auth: gcm.BTN.CLOSE, cbFnc: scwin.btn_close_onclick}
];
com.setMainBtn3(btnOptions, wfm_mainBtn);
*/
com.setSubBtn3 = function(btnOptions, wfm_subBtn) {
	var programAuthority = gcm.CUR_PROGRAM_AUTH;
	
	if(programAuthority.AUTH_CHECK != 'Y')return;

	for(var i=0; i<btnOptions.length; i++){
		try {
			var subItem = btnOptions[i];
			if(eval("programAuthority."+subItem.auth.value) == "Y"){
				var tmpParentIdx = wfm_subBtn.getWindow().btn_sub_generator.insertChild();
				var subBtn = wfm_subBtn.getWindow().btn_sub_generator.getChild(tmpParentIdx, "btn_sub");
				var str = "";
				if(subItem.userStr !== "undefined" && subItem.userStr!="" && subItem.userStr != null){
					str = subItem.userStr;
				}
				else{
					str = subItem.auth.str;
				}
				subBtn.setValue(str);
				subBtn.addClass(subItem.auth.class);
				if (typeof subItem.cbFnc === "function") {
					subBtn.bind("onclick", subItem.cbFnc); 
				}
			}
		} catch (e) {
		
		}
	}
}

/**그리드 수정 개수에 대한 문구 가져감
 * 
 * @date 2021.03.13
 * @memberOf com
 * @author tracom
 * @param {Object} grid 현재 그리드
 * @return {String} 수정된 그리드 개수 문구
 */
com.strModifiedCnt = function(grid){
	
	var str = "";
	try {
		var idx = 0;
	
		if((typeof grid !== "undefined") && (grid !== null)){
			idx = grid.getModifiedIndex().length;
		}
		
		var gridNm = "";
	
		if((idx > 0) && (typeof gcm.GRID_INFO[grid.org_id] !== "undefined") && (gcm.GRID_INFO[grid.org_id] !== null)
				&& (typeof gcm.GRID_INFO[grid.org_id].name !== "undefined") && (gcm.GRID_INFO[grid.org_id].name !== null)){
			gridNm = gcm.GRID_INFO[grid.org_id].name;
		}
		
	
		
		if(idx > 0) {
			str += gridNm + " " + idx + "건";
		}
	} catch (e) {
		
	}
	return str
}

/**복수 그리드 수정 개수에 대한 문구 가져감
 * 
 * @date 2021.03.13
 * @memberOf com
 * @author tracom
 * @param {Object} gridArr 복수 그리드
 * @return {String} 복수 그리드 개수 문구
 */
com.strModifiedCnt2 = function(gridArr){
	var str = "";
	var isFirst = true;
	try {
		for(var i in gridArr){
			grid = gridArr[i];
			
			var idx = 0;
		
			if((typeof grid !== "undefined") && (grid !== null)){
				idx = grid.getModifiedIndex().length;
			}
			
			var gridNm = "";
		
			if((idx > 0) && (typeof gcm.GRID_INFO[grid.org_id] !== "undefined") && (gcm.GRID_INFO[grid.org_id] !== null)
					&& (typeof gcm.GRID_INFO[grid.org_id].name !== "undefined") && (gcm.GRID_INFO[grid.org_id].name !== null)){
				gridNm = gcm.GRID_INFO[grid.org_id].name;
			}
			
		
			
			if(idx > 0) {
				if(isFirst==false){
					str += ", ";
				}
				isFirst = false;
				str += gridNm + " " + idx + "건";
			}

		}
	} catch (e) {
		
	}
	return str
}

/**저장 submission 동작후 처리 : 저장전 상태 초기화 및 화면 검색 submission 동작하여 화면 초기화 함
 * 
 * @date 2021.03.13
 * @memberOf com
 * @author tracom
 * @param {Object} grid1 첫번째 그리드 
 * @param {Object} searchSbmObj1 검색 submission 객체
 * @param {Object} grid2 두번째 그리드
 */
com.saveDone = function(grid1, searchSbmObj1, grid2){
	
	if((typeof grid1 !== "undefined") && (grid1 !== null)){
		com.clearGrid(grid1);
		if((typeof grid2 !== "undefined") && (grid2 !== null)){
			com.clearGrid(grid2);
		}
	}
	if((typeof searchSbmObj1 !== "undefined") && (searchSbmObj1 !== null)){
		com.executeSubmission(searchSbmObj1);
	}
}

/**데이터 저장 가능여부 확인후 저장 프로세스 수행
 * 
 * @date 2021.03.13
 * @memberOf com
 * @author tracom
 * @param {Object} grid 대상 그리드 
 * @param {Object} form 대상 form
 * @param {Object} saveSbmObj 저장 submission 객체
 */
com.saveData = function(grid,form,saveSbmObj){
	
	var isOk = false
	if((typeof form == "undefined")||(form==null)){
		if (com.validateGridView(grid)) {
			isOk = true;
		}	
	}
	else{
		if (com.validateGridTableView(grid, form)) {
			isOk = true;
		}	
	}
	if(isOk==true){
		com.setKeyGridInfo(grid);
		com.executeSubmission(saveSbmObj);
		//com.delUndoGrid(grid);
	}
}

com.setExlUpdate = function(grid, val){
	if(com.isEmpty(gcm.GRID_INFO[grid.org_id])==false && com.isEmpty(gcm.GRID_INFO[grid.org_id].search_map)==false){
		//gcm.GRID_INFO[grid.org_id].exlUpdate = val;
		gcm.GRID_INFO[grid.org_id].search_map.set("EXL_UPDATE", val);
	}
}

com.getExlUpdate = function(grid){
	if(com.isEmpty(gcm.GRID_INFO[grid.org_id])==false && com.isEmpty(gcm.GRID_INFO[grid.org_id].search_map)==false){
		//return gcm.GRID_INFO[grid.org_id].exlUpdate;
		gcm.GRID_INFO[grid.org_id].search_map.get("EXL_UPDATE");
	}
	else return false;
}


/**모든 데이터 저장 가능여부 확인후 저장 프로세스 수행
 * 
 * @date 2021.03.13
 * @memberOf com
 * @author tracom
 * @param {Object} mainGrid 대상 main 그리드
 * @param {Object} subGrid 대상 sub 그리드 
 * @param {Object} form 대상 form
 * @param {Object} saveSbmObj 저장 submission 객체
 */
com.saveAllData = function(mainGrid,subGrid,form,saveSbmObj){

	var isOk = false
	if(	(typeof form == "undefined")||form==null){
		if (com.validateGridView(mainGrid)) {
			isOk = true;
		}
	}
	else{
		if (com.validateGridTableView(mainGrid, form)) {
			isOk = true;
		}	
	}
	if(isOk==true){
		com.setKeyGridInfo(mainGrid);
		com.executeSubmission(saveSbmObj);
	}
	return isOk;
}

/**검색후 grid에 갱신하는 함수 : 수정할 항목이 있으면 저장후 검색되도록 동작함
 * 
 * @date 2021.03.13
 * @memberOf com
 * @author tracom
 * @param {Object} grid 대상 그리드
 * @param {Object} searchSbmObj 검색 submission 객체
 * @param {Object} saveSbmObj 저장 submission 객체
 * @param {Object} param 검색 parameter
 * @param {String} str 저장 동작시 확인여부 문구
 */
com.searchGrid = function(grid,searchSbmObj,saveSbmObj,param, str){
	if(	(typeof saveSbmObj !== "undefined")&&(saveSbmObj !== null)){
		var idx = grid.getModifiedIndex().length;
		//저장하지 않고 조회할 경우
		if(idx > 0) {
			if(	(typeof str == "undefined") || (str.trim() == "")){
				str = com.strModifiedCnt(grid) + "의 저장하지 않은 데이터가 있습니다. 저장 후 조회 하시겠습니까?";
			}
			else {
				str = str + idx;
			}
			com.confirm(str, function(rtn){
				if (rtn) {
					com.saveData(grid,null,saveSbmObj,null)
				}
				else {
					com.clearGrid(grid);
					if((typeof param !== "undefined")&&(param !== null)) com.executeSubmission(searchSbmObj,param);
					else com.executeSubmission(searchSbmObj)
				}
				return;
			});
		}
		else {
			com.clearGrid(grid);
			if((typeof param !== "undefined")&&(param !== null)) com.executeSubmission(searchSbmObj,param);
			else com.executeSubmission(searchSbmObj)
		}
	}
	else{
		com.clearGrid(grid);
		if((typeof param !== "undefined")&&(param !== null)) com.executeSubmission(searchSbmObj,param);
		else com.executeSubmission(searchSbmObj)
	}
}

/**검색후 2개 grid에 갱신하는 함수 : 수정할 항목이 있으면 저장후 검색되도록 동작함
 * 
 * @date 2021.03.13
 * @memberOf com
 * @author tracom
 * @param {Object} mainGrid 대상 그리드
 * @param {Object} subGrid 대상 그리드
 * @param {Object} form 대상 form
 * @param {Object} searchSbmObj 검색 submission 객체
 * @param {Object} mainSaveSbmObj main 저장 submission 객체
 * @param {Object} allSaveSbmObj main/sub 저장 submission 객체
 * @param {Object} subSaveSbmObj sub 저장 submission 객체
 * @param {String} str 저장 동작시 확인여부 문구
 */
com.searchDualGrid = function(mainGrid, subGrid, form, searchSbmObj,mainSaveSbmObj,allSaveSbmObj,subSaveSbmObj,str){
	var modifiedMainCnt = mainGrid.getModifiedIndex().length;
	var modifiedSubCnt = 0;
	if((typeof subGrid.getModifiedIndex !== "undefined")&&(subGrid.getModifiedIndex !== null))
		modifiedSubCnt = subGrid.getModifiedIndex().length;
	
	//저장하지 않고 조회할 경우
	if ((modifiedMainCnt > 0) || (modifiedSubCnt > 0)) {
		if(	(typeof str == "undefined") || (str.trim() == "")){
			str = com.strModifiedCnt2([mainGrid, subGrid]) + "의 저장하지 않은 데이터가 있습니다. 저장 후 조회 하시겠습니까?";
		}
		com.confirm(str, function(rtn){
			if (rtn) {
				if (modifiedMainCnt > 0) {
					gcm.GRID_INFO[mainGrid.org_id].dualSaving = true;
					
					if(	(typeof allSaveSbmObj !== "undefined")&&(allSaveSbmObj !== null)){
						com.saveAllData(mainGrid,subGrid,form,allSaveSbmObj);
					}
					else {
						com.saveData(mainGrid,form,mainSaveSbmObj,null)
					}
				}
				else if (modifiedSubCnt > 0) {
					com.saveData(subGrid,null,subSaveSbmObj,null)
				}
			}
			else {
				com.clearGrid(mainGrid);
				com.clearGrid(subGrid);
				com.executeSubmission(searchSbmObj);
				com.setKeyGridInfo(mainGrid);
			}
		});
	}
	else {
		com.clearGrid(mainGrid);
		com.clearGrid(subGrid);
		com.executeSubmission(searchSbmObj);
	}
}

/**검색후 3개 grid에 갱신하는 함수 : 수정할 항목이 있으면 저장후 검색되도록 동작함
 * 
 * @date 2021.03.13
 * @memberOf com
 * @author tracom
 * @param {Object} mainGrid 대상 그리드
 * @param {Object} subGrid1 대상 그리드
 * @param {Object} subGrid2 대상 그리드
 * @param {Object} form 대상 form
 * @param {Object} searchSbmObj 검색 submission 객체
 * @param {Object} mainSaveSbmObj main 저장 submission 객체
 * @param {Object} allSaveSbmObj main/sub 저장 submission 객체
 * @param {Object} subSaveSbmObj sub 저장 submission 객체
 * @param {String} str 저장 동작시 확인여부 문구
 */
com.searchThirdGrid = function(mainGrid, subGrid1, subGrid2, form, searchSbmObj,mainSaveSbmObj,allSaveSbmObj,subSaveSbmObj1,subSaveSbmObj2,str){
	
	
	var modifiedMainCnt = mainGrid.getModifiedIndex().length;
	var modifiedSubCnt1 = subGrid1.getModifiedIndex().length;
	var modifiedSubCnt2 = subGrid2.getModifiedIndex().length;
	
	//저장하지 않고 조회할 경우
	if ((modifiedMainCnt > 0) || (modifiedSubCnt1 > 0) || (modifiedSubCnt2 > 0)) {
		if(	(typeof str == "undefined") || (str.trim() == "")){
			if (modifiedMainCnt > 0){
				str = com.strModifiedCnt(mainGrid) + "의 저장하지 않은 데이터가 있습니다. 저장 후 조회 하시겠습니까?";
			}
			else if (modifiedSubCnt1 > 0 || modifiedSubCnt2 > 0) {
				str = com.strModifiedCnt2([ subGrid1, subGrid2]) + "의 저장하지 않은 데이터가 있습니다. 저장 후 조회 하시겠습니까?";
			}
		}
		com.confirm(str, function(rtn){
			if (rtn) {
				if (modifiedMainCnt > 0) {
					gcm.GRID_INFO[mainGrid.org_id].dualSaving = true;
					
					if(	(typeof allSaveSbmObj !== "undefined")&&(allSaveSbmObj !== null)){
						com.saveAllData(mainGrid,subGrid,form,allSaveSbmObj);
					}
					else {
						com.saveData(mainGrid,form,mainSaveSbmObj,null)
					}
				}
				else if (modifiedSubCnt1 > 0 || modifiedSubCnt2 > 0) {
					if (modifiedSubCnt1 > 0)
						com.saveData(subGrid1,null,subSaveSbmObj1,null);
					if (modifiedSubCnt2 > 0)
						com.saveData(subGrid2,null,subSaveSbmObj2,null);
				}
			}
			else {
				com.clearGrid(mainGrid);
				com.clearGrid(subGrid1);
				com.clearGrid(subGrid2);
				com.executeSubmission(searchSbmObj);
				com.setKeyGridInfo(mainGrid);
			}
		});
	}
	else {
		com.clearGrid(mainGrid);
		com.clearGrid(subGrid1);
		com.clearGrid(subGrid2);
		com.executeSubmission(searchSbmObj);
	}
}

/**grid 추가 : db에서 key값을 받아와 처리함
 * 
 * @date 2021.03.13
 * @memberOf com
 * @author tracom
 * @param {Object} grid 대상 그리드
 * @param {Object} keyMap key 값을 가지고 있는 map
 * @param {String} keyColumn key column 명
 * @param {String} focusColumn focus column 명
 * @return {Int} 추가된 index
 */

com.addGrid = function(grid,keyMap,keyColumn,focusColumn){
	var data = com.getGridViewDataList(grid);
	var insertIndex = data.insertRow();
	data.setCellData(insertIndex, keyColumn, keyMap.get("SEQ"));
	grid.setFocusedCell(insertIndex, focusColumn, true);
	return insertIndex;
}

/**grid 추가
 * 
 * @date 2021.03.13
 * @memberOf com
 * @author tracom
 * @param {Object} grid 대상 그리드
 * @param {String} focusColumn 대상 form
 * @return {Int} 추가된 index
 */
com.addGrid2 = function(grid,focusColumn){
	var data = com.getGridViewDataList(grid);
	var insertIndex = data.insertRow();
	grid.setFocusedCell(insertIndex, focusColumn, true);
	return insertIndex;
}

/**grid 추가 : db에서 key값을 받아와 처리함, form 포커스 처리
 * 
 * @date 2021.03.13
 * @memberOf com
 * @author tracom
 * @param {Object} grid 대상 그리드
 * @param {Object} form 대상 form
 * @param {Object} keyMap key 값을 가지고 있는 map
 * @param {String} keyColumn key column 명
 * @param {String} focusColumn focus column 명
 * @return {Int} 추가된 index
 */
com.addGridForm = function(grid,form,keyMap,keyColumn,focusID){
	var data = com.getGridViewDataList(grid);
	var insertIndex = data.insertRow();
	data.setCellData(insertIndex, keyColumn, keyMap.get("SEQ"));
	if(	(typeof form !== "undefined")&&(form !== null)){
		grid.setFocusedCell(insertIndex, focusID, true);
		$p.getComponentById(focusID).focus()
	}
	else{
		grid.setFocusedCell(insertIndex, focusID, true);
	}
	return insertIndex;
}

/**grid에 수정된 항목 취소
 * 
 * @date 2021.03.13
 * @memberOf com
 * @author tracom
 * @param {Object} grid 대상 그리드
 * @param {String} str 수정된 항목이 있을때 확인 문구
 * @param {fnc} cnlGridCb 취소 이후 callback 함수
 */
com.cancelGrid = function(grid,str,cnlGridCb){

	var idx = grid.getModifiedIndex().length;

	if (idx > 0) {
		if(str== null || (typeof str == "undefined") || (str.trim() == "")){
			str = com.strModifiedCnt(grid) + "의 저장하지 않은 데이터가 있습니다. 취소 하시겠습니까?";
		}
		else {
			str = idx + "건의 저장하지 않은 데이터가 있습니다. 취소 하시겠습니까?";
		} 
		com.confirm(str, function(rtn) {
			if (rtn) {
				com.clearGrid(grid,cnlGridCb);
				var index = grid.getFocusedRowIndex();
				var rowCount = com.getGridViewDataList(grid).getRowCount();
				if(grid.getFocusedRowIndex() >= rowCount){
					grid.setFocusedCell((rowCount-1), grid.getColumnID(0), true);
				};
			}
		});
	}
}

/**2개 grid에 수정된 항목 취소
 * 
 * @date 2021.03.13
 * @memberOf com
 * @author tracom
 * @param {Object} mainGrid 대상 main 그리드
 * @param {Object} subGrid 대상 sub 그리드
 * @param {fnc} mainGridCb 취소 이후 main callback 함수
 * @param {fnc} subGridCb 취소 이후 sub callback 함수
 */
com.cancelDualGrid = function(mainGrid,subGrid,mainGridCb,subGridCb){

	var mainIdx = mainGrid.getModifiedIndex().length;
	var subIdx = subGrid.getModifiedIndex().length;

	if (mainIdx > 0 || subIdx > 0) {
		
		var str = com.strModifiedCnt2([mainGrid,subGrid]) + "의 저장하지 않은 데이터가 있습니다. 취소 하시겠습니까?";
		com.confirm(str, function(rtn) {
			if (rtn) {
				com.clearGrid(mainGrid);
				if((mainGridCb != null) && (typeof mainGridCb != "undefined") && (typeof mainGridCb == "function")){
					mainGridCb();
				}
				com.clearGrid(subGrid);
				if((subGridCb != null) && (typeof subGridCb != "undefined") && (typeof subGridCb == "function")){
					subGridCb();
				}
			}
		});
	}
}

/**3개 grid에 수정된 항목 취소
 * 
 * @date 2021.03.13
 * @memberOf com
 * @author tracom
 * @param {Object} mainGrid 대상 main 그리드
 * @param {Object} subGrid1 대상 sub1 그리드
 * @param {Object} subGrid2 대상 sub2 그리드
 */
com.cancelThirdGrid = function(mainGrid,subGrid1,subGrid2){

	var mainIdx = mainGrid.getModifiedIndex().length;
	var subIdx1 = subGrid1.getModifiedIndex().length;
	var subIdx2 = subGrid2.getModifiedIndex().length;

	if (mainIdx > 0 || subIdx1 > 0 || subIdx2 > 0) {
		
		var str = com.strModifiedCnt2([mainGrid,subGrid1,subGrid2]) + "의 저장하지 않은 데이터가 있습니다. 취소 하시겠습니까?";
		com.confirm(str, function(rtn) {
			if (rtn) {
				com.clearGrid(mainGrid);
				com.clearGrid(subGrid1);
				com.clearGrid(subGrid2);
			}
		});
	}
}

/**grid의 모든 항목을 화면에 보이도록 함
 * 
 * @date 2021.03.13
 * @memberOf com
 * @author tracom
 * @param {Object} grid 대상 그리드
 */
com.allVisibleGrid = function(grid){
	var data = com.getGridViewDataList(grid);

	for (var i = 0; i < data.getTotalRow(); i++) {
		grid.setRowVisible(i, true);
	}
}

/**grid의 모든 항목의 상태를 되돌림
 * 
 * @date 2021.03.13
 * @memberOf com
 * @author tracom
 * @param {Object} grid 대상 그리드
 */
com.delUndoGrid = function(grid){
	var data = com.getGridViewDataList(grid);
	if((typeof data == "undefined")||(data == null))return;
	
	var deletedIdx = data.getDeletedIndex();
	
	if((deletedIdx.length!=0)&&(deletedIdx.length == data.getTotalRow())){
		grid.initGrid();
	}
	else {
		for (var i = 0; i < deletedIdx.length; i++) {
			grid.setRowVisible(deletedIdx[i], true);
		}
	}
}

/**grid의 화면 및 상태를 초기화 함
 * 
 * @date 2021.03.13
 * @memberOf com
 * @author tracom
 * @param {Object} grid 대상 그리드
 * @param {fnc} cnlGridCb 초기화 이후에 호출되는 callback 함수
 */
com.clearGrid = function(grid, cnlGridCb){
	if(com.isEmpty(gcm.GRID_INFO[grid.org_id])==false){
		//gcm.GRID_INFO[grid.org_id].exlUpdate = false; //엑셀 업데이트도 초기화
		com.setExlUpdate(grid,false);
	}
	
	if(com.isEmpty(gcm.GRID_INFO[grid.org_id])==false && com.isEmpty(gcm.GRID_INFO[grid.org_id].undo)==false
			 && gcm.GRID_INFO[grid.org_id].undo == false)return;

	var data = com.getGridViewDataList(grid);

	com.delUndoGrid(grid);
	
	data.undoAll();
	data.removeRows(data.getInsertedIndex());
	if((cnlGridCb != null) && (typeof cnlGridCb != "undefined") && (typeof cnlGridCb == "function")){
		cnlGridCb();
	}
}

/**포커스된 grid 삭제
 * 
 * @date 2021.03.13
 * @memberOf com
 * @author tracom
 * @param {Object} grid 대상 그리드
 * @param {String} str 삭제시 확인 문구
 * @param {fnc} afterCb 삭제 이후에 호출되는 callback 함수
 */
com.delGrid = function(grid,str,afterCb){
	var data = com.getGridViewDataList(grid);
	var focusIdxs = grid.getAllFocusedRowIndex();
	var count = focusIdxs.length;
	if (count > 0) {
		if(	(str == null) || (typeof str == "undefined") || (str.trim() == "")){
			var mainNm = "";
			if(	(typeof gcm.GRID_INFO[grid.org_id] !== "undefined") && (gcm.GRID_INFO[grid.org_id] !== null) 
					&&(gcm.GRID_INFO[grid.org_id].name !== null)&&(typeof gcm.GRID_INFO[grid.org_id].name !== "undefined")){
				mainNm = gcm.GRID_INFO[grid.org_id].name;
			}
			str = mainNm + " " + count + "건의 데이터를 삭제 하시겠습니까?";
		}
		else {
			str = count + str;
		}
		com.confirm(str, function(rtn) {
			if (rtn) {
				
				for(var i=count-1; i>=0; i--){
					var isCreate = false;
					try {
						var modifiedIdx = data.getModifiedIndex();

						for (var j = 0; j < modifiedIdx.length; j++) {
							var index = modifiedIdx[j];
							if(index==focusIdxs[i]){
								var modifiedData = data.getRowJSON(index);
								if (modifiedData.rowStatus === "C") { //생성된 경우 visible 하지 않고 삭제함 //getInsertedIndex 로 대신 구현해도 됨
									isCreate = true;
								}
								break;
							}
						}
					} catch (e) {
						$p.log("[com.delGrid] Exception :: " + e.message);
					}	
				
					if(isCreate==true){ //생성된 경우 데이터에서 삭제함
						data.removeRow(focusIdxs[i]);
					}
					else {
						grid.setRowVisible(focusIdxs[i], false);
						data.deleteRow(focusIdxs[i]);
					}
				}
				
				var focusIndex = 0;
				
				if(count>0){
					for(var i=0; i<count; i++){
						if(grid.getRowVisible(focusIdxs[i])){
							focusIndex = focusIdxs[i];
							break;
						}
					}
							
					if(i==count){
						if((focusIdxs[count-1] +1)<data.getTotalRow()){
							focusIndex = focusIdxs[count-1] +1;
						}
						else{
							focusIndex = data.getTotalRow();
						}
					}
				}

				grid.setFocusedCell(focusIndex, 0, false);
			
				
				if((afterCb != null) && (typeof afterCb != "undefined") && (typeof afterCb == "function")){
					afterCb();
				}
			}		
		});
	}
}

/**포커스된 grid 삭제
 * 
 * @date 2021.03.13
 * @memberOf com
 * @author tracom
 * @param {Object} grid 대상 그리드
 * @param {fnc} afterCb 삭제 이후에 호출되는 callback 함수
 */
com.delGrid2 = function(grid,afterCb){
	var data = com.getGridViewDataList(grid);
	var focusIdxs = grid.getAllFocusedRowIndex();
	var count = focusIdxs.length;
	if (count > 0) {
		for(var i=count-1; i>=0; i--){
			var isCreate = false;
			try {
				var modifiedIdx = data.getModifiedIndex();

				for (var j = 0; j < modifiedIdx.length; j++) {
					var index = modifiedIdx[j];
					if(index==focusIdxs[i]){
						var modifiedData = data.getRowJSON(index);
						if (modifiedData.rowStatus === "C") { //생성된 경우 visible 하지 않고 삭제함 //getInsertedIndex 로 대신 구현해도 됨
							isCreate = true;
						}
						break;
					}
				}
			} catch (e) {
				$p.log("[com.delGrid] Exception :: " + e.message);
			}	
		
			if(isCreate==true){ //생성된 경우 데이터에서 삭제함
				data.removeRow(focusIdxs[i]);
			}
			else {
				grid.setRowVisible(focusIdxs[i], false);
				data.deleteRow(focusIdxs[i]);
			}
		}
	}
	if((afterCb != null) && (typeof afterCb != "undefined") && (typeof afterCb == "function")){
		afterCb();
	}
}

/**선택된 grid 삭제
 * 
 * @date 2021.03.13
 * @memberOf com
 * @author tracom
 * @param {Object} grid 대상 그리드
 * @param {String} str 삭제시 확인 문구
 * @param {fnc} afterCb 삭제 이후에 호출되는 callback 함수
 */
com.delGridChecked = function(grid,str,afterCb){
	var data = com.getGridViewDataList(grid);
	var focusIdxs = grid.getCheckedIndex("chk");
	var count = focusIdxs.length;
	if (count > 0) {
		if(	(str == null) || (typeof str == "undefined") || (str.trim() == "")){
			var mainNm = "";
			if(	(typeof gcm.GRID_INFO[grid.org_id] !== "undefined") && (gcm.GRID_INFO[grid.org_id] !== null) 
					&&(gcm.GRID_INFO[grid.org_id].name !== null)&&(typeof gcm.GRID_INFO[grid.org_id].name !== "undefined")){
				mainNm = gcm.GRID_INFO[grid.org_id].name;
			}
			str = mainNm + " " + count + "건의 데이터를 삭제 하시겠습니까?";
		}
		else {
			str = count + str;
		}
		com.confirm(str, function(rtn) {
			if (rtn) {
				
				for(var i=count-1; i>=0; i--){
					var isCreate = false;
					try {
						var modifiedIdx = data.getModifiedIndex();

						for (var j = 0; j < modifiedIdx.length; j++) {
							var index = modifiedIdx[j];
							if(index==focusIdxs[i]){
								var modifiedData = data.getRowJSON(index);
								if (modifiedData.rowStatus === "C") { //생성된 경우 visible 하지 않고 삭제함 //getInsertedIndex 로 대신 구현해도 됨
									isCreate = true;
								}
								break;
							}
						}
					} catch (e) {
						$p.log("[com.delGrid] Exception :: " + e.message);
					}	
				
					if(isCreate==true){ //생성된 경우 데이터에서 삭제함
						data.removeRow(focusIdxs[i]);
					}
					else {
						grid.setRowVisible(focusIdxs[i], false);
						data.deleteRow(focusIdxs[i]);
					}
				}
				
				var focusIndex = 0;
				
				if(count>0){
					for(var i=0; i<count; i++){
						if(grid.getRowVisible(focusIdxs[i])){
							focusIndex = focusIdxs[i];
							break;
						}
					}
							
					if(i==count){
						if((focusIdxs[count-1] +1)<data.getTotalRow()){
							focusIndex = focusIdxs[count-1] +1;
						}
						else{
							focusIndex = data.getTotalRow();
						}
					}
				}

				grid.setFocusedCell(focusIndex, 0, false);
			
				
				if((afterCb != null) && (typeof afterCb != "undefined") && (typeof afterCb == "function")){
					afterCb();
				}
			}		
		});
	}
}

com.deleteAllGrid = function(grid){
	var data = com.getGridViewDataList(grid);
	var count = data.getTotalRow();

	for(var i=count-1; i>=0; i--){
		data.deleteRow(i);
	}
}

/** grid의 모든 항목 삭제
 * 
 * @date 2021.03.13
 * @memberOf com
 * @author tracom
 * @param {Object} grid 대상 그리드
 */
com.removeAllGrid = function(grid){
	var data = com.getGridViewDataList(grid);
	var count = data.getTotalRow();

	for(var i=count-1; i>=0; i--){
		data.removeRow(i);
	}
}

/** 2개(부모,자식) 그리드 삭제
 * 
 * @date 2021.03.13
 * @memberOf com
 * @author tracom
 * @param {Object} mainGrid 대상 main 그리드
 * @param {Object} subGrid 대상 sub 그리드
 * @param {String} str 삭제시 확인 문구
 */
com.delDualGrid = function(mainGrid,subGrid,str){ //듀얼 그리드에서 부모 자식 삭제
	var data = com.getGridViewDataList(mainGrid);
	var focusIdxs = mainGrid.getAllFocusedRowIndex();
	var count = focusIdxs.length;
	if (count > 0) {
		if(	(typeof str == "undefined") || (str.trim() == "")){
			var mainNm = "";
			if(	(typeof gcm.GRID_INFO[mainGrid.org_id] !== "undefined") && (gcm.GRID_INFO[mainGrid.org_id] !== null) 
					&&(gcm.GRID_INFO[mainGrid.org_id].name !== null)&&(typeof gcm.GRID_INFO[mainGrid.org_id].name !== "undefined")){
				mainNm = gcm.GRID_INFO[mainGrid.org_id].name;
			}
			str = mainNm + " " + count + "건의 데이터를 삭제 하시겠습니까?";
		}
		else {
			str = count + str;
		}
		com.confirm(str, function(rtn) {
			if (rtn) {
				for(var i=count-1; i>=0; i--){
					var isCreate = false;
					try {
						var modifiedIdx = data.getModifiedIndex();

						for (var j = 0; j < modifiedIdx.length; j++) {
							var index = modifiedIdx[j];
							if(index==focusIdxs[i]){
								var modifiedData = data.getRowJSON(index);
								if (modifiedData.rowStatus === "C") { //생성된 경우 visible 하지 않고 삭제함
									isCreate = true;
								}
								break;
							}
						}
					} catch (e) {
						$p.log("[com.delGrid] Exception :: " + e.message);
					}	
				
					if(isCreate==true){ //생성된 경우 데이터에서 삭제함
						data.removeRow(focusIdxs[i]);
					}
					else {
						mainGrid.setRowVisible(focusIdxs[i], false);
						data.deleteRow(focusIdxs[i]);
					}
				}
				
				
				var focusIndex = 0;
				
				if(count>0){
					if((focusIdxs[count-1] +1)<data.getTotalRow()){
						focusIndex = focusIdxs[count-1] +1;
					}
					else{
						focusIndex = data.getTotalRow();
					}
				}

				mainGrid.setFocusedCell(focusIndex, 0, true);
				
				if(	(typeof subGrid != "undefined") && (subGrid != null)){
					var subData = com.getGridViewDataList(subGrid);
					subData.removeAll();
				}
			}		
		});
	}
}

/** 3개(부모,자식1, 자식2) 그리드 삭제
 * 
 * @date 2021.03.13
 * @memberOf com
 * @author tracom
 * @param {Object} mainGrid 대상 main 그리드
 * @param {Object} subGrid1 대상 sub1 그리드
 * @param {Object} subGrid2 대상 sub2 그리드
 * @param {String} str 삭제시 확인 문구
 */
com.delThirdGrid = function(mainGrid,subGrid1,subGrid2,str){ //3개 그리드에서 부모 자식 삭제
	var data = com.getGridViewDataList(mainGrid);
	var focusIdxs = mainGrid.getAllFocusedRowIndex();
	var count = focusIdxs.length;
	if (count > 0) {
		if(	(typeof str == "undefined") || (str.trim() == "")){
			var mainNm = "";
			if(	(typeof gcm.GRID_INFO[mainGrid.org_id] !== "undefined") && (gcm.GRID_INFO[mainGrid.org_id] !== null) 
					&&(gcm.GRID_INFO[mainGrid.org_id].name !== null)&&(typeof gcm.GRID_INFO[mainGrid.org_id].name !== "undefined")){
				mainNm = gcm.GRID_INFO[mainGrid.org_id].name;
			}
			str = mainNm + " " + count + "건의 데이터를 삭제 하시겠습니까?";
		}
		else {
			str = count + str;
		}
		com.confirm(str, function(rtn) {
			if (rtn) {
				for(var i=count-1; i>=0; i--){
					var isCreate = false;
					try {
						var modifiedIdx = data.getModifiedIndex();

						for (var j = 0; j < modifiedIdx.length; j++) {
							var index = modifiedIdx[j];
							if(index==focusIdxs[i]){
								var modifiedData = data.getRowJSON(index);
								if (modifiedData.rowStatus === "C") { //생성된 경우 visible 하지 않고 삭제함
									isCreate = true;
								}
								break;
							}
						}
					} catch (e) {
						$p.log("[com.delGrid] Exception :: " + e.message);
					}	
				
					if(isCreate==true){ //생성된 경우 데이터에서 삭제함
						data.removeRow(focusIdxs[i]);
					}
					else {
						mainGrid.setRowVisible(focusIdxs[i], false);
						data.deleteRow(focusIdxs[i]);
					}
				}
				
				var focusIndex = 0;
				
				if(count>0){
					if((focusIdxs[count-1] +1)<data.getTotalRow()){
						focusIndex = focusIdxs[count-1] +1;
					}
					else{
						focusIndex = data.getTotalRow();
					}
				}

				mainGrid.setFocusedCell(focusIndex, 0, true);
				
				if(	(typeof subGrid1 != "undefined") && (subGrid1 != null)){
					com.getGridViewDataList(subGrid1).removeAll();
				}
				if(	(typeof subGrid2 != "undefined") && (subGrid2 != null)){
					com.getGridViewDataList(subGrid2).removeAll();
				}
			}		
		});
	}
}

/** 그리드의 수정된 항목 저장
 * 
 * @date 2021.03.13
 * @memberOf com
 * @author tracom
 * @param {Object} grid 대상 그리드
 * @param {String} yesno_str 저장시 yes no 팝업 문구
 * @param {String} str 저장 오류시 alert 문구
 */
com.saveGrid = function(grid,sbmObj,yesno_str,str){
	var idx = grid.getModifiedIndex().length;
	
	if (idx == 0) {
		if(	(typeof str == "undefined") || (str.trim() == ""))
			str = "저장할 데이터가 없습니다";
		com.alert(str);
		return false;
	}

	if (com.validateGridView(grid)) {
		if (idx > 0) {
			if(	(typeof yesno_str == "undefined") || (yesno_str.trim() == "")){
				yesno_str = com.strModifiedCnt(grid) + "의 데이터가 저장됩니다. \n 진행하시겠습니까?";
			}
			else {
				yesno_str = idx + yesno_str;
			}
			com.confirm(yesno_str, function(rtn) {
				if (rtn) {
					com.saveData(grid,null,sbmObj);
				}
			});
		}
	}
}

com.saveExlGrid = function(grid,sbmObj, param){
	var idx = grid.getModifiedIndex().length;
	
	if (idx == 0) {
		if(	(typeof str == "undefined") || (str.trim() == ""))
			str = "저장할 데이터가 없습니다";
		com.alert(str);
		return false;
	}

	if (com.validateGridView(grid)) {
		var yesno_str = "";
		if (idx > 0) {
			if(	(typeof yesno_str == "undefined") || (yesno_str.trim() == "")){
				yesno_str = com.strModifiedCnt(grid) + "의 데이터가 저장됩니다. \n 진행하시겠습니까?";
			}
			else {
				yesno_str = idx + yesno_str;
			}
			com.confirm(yesno_str, function(rtn) {
				if (rtn) {
					com.saveData(grid,null,sbmObj);
				}
			});
		}
	}
}

/** 2개(부모,자식)그리드의 수정된 항목 저장
 * 
 * @date 2021.03.13
 * @memberOf com
 * @author tracom
 * @param {Object} mainGrid 대상 main 그리드
 * @param {Object} subGrid 대상 sub 그리드
 * @param {Object} mainSaveSbmObj main 그리드 저장 submission
 * @param {Object} allSaveSbmObj 그리드 전체 저장 submission
 * @param {Object} subSaveSbmObj sub 그리드 저장 submission
 */
com.saveDualGrid = function(mainGrid,subGrid,form,mainSaveSbmObj,allSaveSbmObj,subSaveSbmObj){ //듀얼 그리드에서 부모 자식 저장
	var mainData = com.getGridViewDataList(mainGrid);
	var subData = com.getGridViewDataList(subGrid);
	
	var modifiedMainIdx = mainData.getModifiedIndex();
	var modifiedSubIdx = subData.getModifiedIndex();
	
	var confirmStr = com.strModifiedCnt2([mainGrid, subGrid]);
	
	if (modifiedMainIdx.length > 0) {	
			com.confirm(confirmStr+"의 데이터가 저장됩니다. \n 진행하시겠습니까?", function(rtn) {
				if (rtn) {
					gcm.GRID_INFO[mainGrid.org_id].dualSaving = true;
					
					if((typeof allSaveSbmObj !== "undefined")){
						com.saveAllData(mainGrid,subGrid,form,allSaveSbmObj);
					}
					else if((typeof mainSaveSbmObj !== "undefined")){
						com.saveData(mainGrid,form,mainSaveSbmObj);
					}
				}
			});
	} else if (modifiedSubIdx.length > 0) {
		com.confirm(confirmStr+"의 데이터가 저장됩니다. \n 진행하시겠습니까?", function(rtn) {
			if (rtn) {
				com.saveData(subGrid,null,subSaveSbmObj);
			}
		});
	} else {
		com.alert("저장할 데이터가 없습니다.");
	}
}

/** 3개(부모,자식1, 자식2)그리드의 수정된 항목 저장
 * 
 * @date 2021.03.13
 * @memberOf com
 * @author tracom
 * @param {Object} mainGrid 대상 main 그리드
 * @param {Object} subGrid1 대상 sub1 그리드
 * @param {Object} subGrid2 대상 sub2 그리드
 * @param {Object} mainSaveSbmObj main 그리드 저장 submission
 * @param {Object} allSaveSbmObj 그리드 전체 저장 submission
 * @param {Object} subSaveSbmObj1 sub1 그리드 저장 submission
 * @param {Object} subSaveSbmObj1 sub2 그리드 저장 submission
 */
com.saveThirdGrid = function(mainGrid,subGrid1,subGrid2,form,mainSaveSbmObj,allSaveSbmObj,subSaveSbmObj1,subSaveSbmObj2){ //3개의 그리드에서 부모 자식 저장
	var mainData = com.getGridViewDataList(mainGrid);
	var subData1 = com.getGridViewDataList(subGrid1);
	var subData2 = com.getGridViewDataList(subGrid2);
	
	var modifiedMainIdx = mainData.getModifiedIndex();
	var modifiedSubIdx1 = subData1.getModifiedIndex();
	var modifiedSubIdx2 = subData2.getModifiedIndex();
	
	
	if (modifiedMainIdx.length > 0) {	
		var confirmStr = com.strModifiedCnt(mainGrid);
		com.confirm(confirmStr+"의 데이터가 저장됩니다. \n 진행하시겠습니까? ", function(rtn) {
			if (rtn) {
				gcm.GRID_INFO[mainGrid.org_id].dualSaving = true;
				if((typeof allSaveSbmObj !== "undefined")){
					com.saveData(mainGrid,form,allSaveSbmObj);
				}
				else if((typeof mainSaveSbmObj !== "undefined")){
					com.saveData(mainGrid,form,mainSaveSbmObj);
				}
			}
		});
	} else if ((modifiedSubIdx1.length > 0)||(modifiedSubIdx2.length > 0)) {
		var confirmStr = com.strModifiedCnt2([subGrid1, subGrid2]);
		com.confirm(confirmStr+"의 데이터가 저장됩니다. \n 진행하시겠습니까? ", function(rtn) {
			if (rtn) {
				if(modifiedSubIdx1.length > 0)
					com.saveData(subGrid1,null,subSaveSbmObj1);
				if(modifiedSubIdx2.length > 0)
					com.saveData(subGrid2,null,subSaveSbmObj2);
			}
		});
	} else {
		com.alert("저장할 데이터가 없습니다.");
	}
}

/** 그리드,form의 수정된 항목 저장
 * 
 * @date 2021.03.13
 * @memberOf com
 * @author tracom
 * @param {Object} grid 대상 그리드
 * @param {Object} form 대상 form
 * @param {Object} sbmObj 그리드 저장 submission
 * @param {Object} searchSbmObj 그리드 검색 submission
 * @param {String} yesno_str 저장시 yes no 팝업 문구
 * @param {String} str 저장 오류시 alert 문구
 */
com.saveGridForm = function(grid,form,sbmObj,searchSbmObj,yesno_str,str){
	var idx = grid.getModifiedIndex().length;
	if (idx == 0) {
		if(	(typeof str == "undefined") || (str.trim() == ""))
			str = "저장할 데이터가 없습니다";
		com.alert(str);
		return false;
	}

	if (idx > 0) {
		if (com.validateGridTableView(grid, form)) {
			if(	(typeof yesno_str == "undefined") || (yesno_str.trim() == "")){
				
				yesno_str = com.strModifiedCnt(grid) + "의 데이터를 반영하시겠습니까?";
			}
			else {
				yesno_str = idx + yesno_str;
			}
			
			com.confirm(yesno_str, function(rtn) {
				if (rtn) {
					com.saveData(grid,form,sbmObj);
				}
			});
		}
	}
}

/** 엑셀 다운로드
 * 
 * @date 2021.03.13
 * @memberOf com
 * @author tracom
 * @param {Object} grid 대상 그리드
 * @param {String} str 다운받을 파일이름
 */
com.exlGrid = function(exlGrid,grid,str){
	if(	(typeof str == "undefined") || (str.trim() == "")){
		if(	typeof grid != "undefined"){
			str = gcm.GRID_INFO[grid.org_id].name; //com.getParameter().menuNm;
		}
		else {
			str = gcm.GRID_INFO[exlGrid.org_id].name; //com.getParameter().menuNm;
		}
	}

	
	var options = {
		fileName : str+".xls", //[defalut: excel.xlsx] 다운로드하려는 파일의 이름으로 필수 입력 값 (지원하는 타입 => xls, xlsx, csv)
		fileType : "xls"
	};

	var infoArr = {};
	com.gridDataDownLoad(exlGrid,options, infoArr);
}

/**	 
 * 엑셀의 데이터를 그리드뷰로 업로드 한다.
 * 필요한 옵션이 있다면 options배열에 옵션을 넣는다.
 * 
 * @date 2021.03.13
 * @memberOf com
 * @author tracom
 * @param {Object} grid 대상 그리드
 */
com.exlUploadGrid = function(grid, codeOptions){
	var str = "엑셀 업로드시 " +gcm.GRID_INFO[grid.org_id].name + "의 데이터가 재 갱신 됩니다. 엑셀 업로드를 하시겠습니까?" ;
	com.confirm(str, function(rtn) {
		if (rtn) {
			//com.deleteAllGrid(grid);
			var options = {};
			if(com.isEmpty(codeOptions)==false){
				var codeDataList = []
				for (var i = 0; i < codeOptions.length; i++) {
					var codeObj = codeOptions[i];
					var dltId = gcm.DATA_PREFIX + codeObj.code;
					var dataListObj = $p.getComponentById(dltId);
					codeDataList[codeObj.code] = dataListObj.getAllJSON();
				}
				options.codeDataList = codeDataList;
				gcm.GRID_INFO[grid.org_id].codeDataList = codeDataList;
			}
			com.gridDataUpload(grid, "xls", options);
		}
	});
}


/**
 * 그리드 Excel 포맷을 다운로드 하는 기능 ( 업로드용  Excel 포맷 )
 * @date 2021.03.13
 * @memberOf com
 * @author tracom
 * @param {Object} grid 대상 그리드
 */
com.exlFrmGrid = function(grid){
	
	
	var gridInfo = gcm.GRID_INFO[grid.org_id];
	
	var options = {};
	options.fileName = gridInfo.name + "_양식";
	options.path = top.window.location.href + "exl/"+grid.org_id +".xlsx";
	//options.dataProvider = "com.inswave.template.provider.ExcelDownHeader";
	var infoArr = {};
	
	  var element = document.createElement('a');
      element.setAttribute('href',options.path);
      element.setAttribute('download', options.fileName);
      document.body.appendChild(element);
      element.click();
	//com.gridDataDownLoad(grid, options , infoArr);	
}

/**검색후 grid,form에 갱신하는 함수 : 수정할 항목이 있으면 저장후 검색되도록 동작함
 * 
 * @date 2021.03.13
 * @memberOf com
 * @author tracom
 * @param {Object} grid 대상 그리드
 * @param {Object} form 대상 form
 * @param {Object} searchSbmObj 검색 submission 객체
 * @param {Object} saveSbmObj 저장 submission 객체
 * @param {String} str 저장 동작시 확인여부 문구
 */
com.searchGridForm = function(grid,form,searchSbmObj,saveSbmObj,str){
	
	if(	(typeof saveSbmObj !== "undefined")&&(saveSbmObj !== null)){
		var idx = grid.getModifiedIndex().length;
		
		//저장하지 않고 조회할 경우
		if(idx > 0) {
			if(	(typeof str == "undefined") || (str.trim() == "")){
				str = com.strModifiedCnt(grid) + "의 저장하지 않은 데이터가 있습니다. 저장 후 조회 하시겠습니까?";
			}
			else {
				str = idx + str;
			}
			com.confirm(str, function(rtn){
				if (rtn) {
					com.saveData(grid,form,saveSbmObj)
				}
				else {
					com.clearGrid(grid);
					com.executeSubmission(searchSbmObj);
				}
				return;
			});
		} 
		else {
			com.clearGrid(grid);
			com.executeSubmission(searchSbmObj);
		}
	}
	else {
		com.clearGrid(grid);
		com.executeSubmission(searchSbmObj);
	}
}

/**탭 닫음
 * 
 * @date 2021.03.13
 * @memberOf com
 * @author tracom
 */
com.tabClose = function(){
	var tabComp = $w.getComponentById("mf_tac_layout");
	var param = com.getParameter();
	if ((typeof param !== "undefined") && (typeof param.menuCode !== "undefined") && (param.menuCode.trim() !== "")) {
		var menuCode = param.menuCode;
		if (tabComp.getSelectedTabID() == menuCode) {
			var tabIndex = tabComp.getTabIndex(menuCode);
			tabComp.deleteTab(tabIndex);
		}
	}
}

/** 탭 닫음 : 수정할 항목이 있으면 yes no 팝업 출력후 탭 닫음 
 * 
 * @date 2021.03.13
 * @memberOf com
 * @author tracom
 * @param {Object} mainGrid 대상 main 그리드
 * @param {Object} subGrid 대상 sub 그리드
 * @param {String} yesno_str 저장시 yes no 팝업 문구
 */
com.closeTab = function(mainGrid, subGrid, yesno_str){
	var mainIdx = mainGrid.getModifiedIndex().length;
	var subIdx = 0;
	if ((typeof subGrid !== "undefined") && (subGrid!=null)){
		subIdx = subGrid.getModifiedIndex().length;
	}
	
	if (mainIdx > 0 || subIdx > 0) {
		if(	(typeof yesno_str == "undefined") || (yesno_str.trim() == ""))
			yesno_str = "저장하지 않은 데이터가 있습니다. 닫으시겠습니까?";
		com.confirm(yesno_str, function(rtn) {
			if (rtn) {
				setTimeout(function() {
					com.tabClose();	
				},100);
			}
			return;
		});
	}
	else com.tabClose();
}

/** grid에서 특정 column 들의 값 가지고 index를 찾음
 * 
 * @date 2021.03.13
 * @memberOf com
 * @author tracom
 * @param {Object} data 대상 그리드 data
 * @param {Object} indexs index 배열
 * @param {Object} column 검색할 column 명
 * @param {String} value index를 찾을 값
 */
com.recurMatchedIndex = function(data, indexs, column, value){

	var retValue  = new Array();
	for (var i = 0; i < indexs.length; i++) {
		if(eval("data.getRowJSON(indexs[i])."+column)==value){
			retValue.push(indexs[i]);
		}
	}
	return retValue;
}

/** grid에서 특정 column 들의 값 가지고 index를 찾음
 * 
 * @date 2021.03.13
 * @memberOf com
 * @author tracom
 * @param {Object} data 대상 그리드 data
 * @param {Object} keyColumn 검색할 column 명
 * @param {String} keyValue index를 찾을 값
 * 
 */
com.getMatchedIndex = function(data, keyColumn, keyValue){
	var keyColumnArr = keyColumn.split(',');
	var keyValueArr = keyValue.split(',');
	
	var indexs = data.getMatchedIndex(keyColumnArr[0], keyValueArr[0]);

	for (var i = 1; i < keyColumnArr.length; i++) {
		indexs = com.recurMatchedIndex(data, indexs, keyColumnArr[i], keyValueArr[i]);
	}
	return indexs;
}

/** grid에서  특정 column으로 포커스 이동
 * 
 * @date 2021.03.13
 * @memberOf com
 * @author tracom
 * @param {Object} grid 대상 그리드
 * @param {Object} focusColumn 포커스할 column 명
 * 
 */
com.setFocusedCell = function(grid, focusColumn) {
	
	try{
		var gridInfo = gcm.GRID_INFO[grid.org_id];
		if ((typeof gridInfo != "undefined") && (gridInfo!=null)){
			if(gridInfo.CUR_ROW_INDEX==-1 || (typeof gridInfo.keyValue == "undefined") || (gridInfo.keyColumn == null)){
				grid.setFocusedCell(0, focusColumn);
			}
			else {
				var data = com.getGridViewDataList(grid);
				var curIndex = com.getMatchedIndex(data, gridInfo.keyColumn, gridInfo.keyValue);
				//var curIndex = data.getMatchedIndex(gridInfo.keyColumn, gridInfo.keyValue);
				grid.setFocusedCell(curIndex, focusColumn);
			}
		}
		else{
			grid.setFocusedCell(0, focusColumn);
		}
	} catch (e) {
		
	}
};

/** grid에서 특정 값을 가진 column으로 포커스 이동
 * 
 * @date 2021.03.13
 * @memberOf com
 * @author tracom
 * @param {Object} grid 대상 그리드
 * @param {Object} searchColumn 검색할 column 명
 * @param {Object} searchValue 검색할 column 값
 * 
 */
com.setFocusedCell2 = function(grid, searchColumn, searchValue) {
	
	try{
		var data = com.getGridViewDataList(grid);
		var curIndex = data.getMatchedIndex(searchColumn, searchValue);
		grid.setFocusedCell(curIndex, searchColumn);
	} catch (e) {
		
	}
};

/** grid정보 가지고 있는 object 초기화
 * 
 * @date 2021.03.13
 * @memberOf com
 * @author tracom
 * @param {Object} options 초기화 할 옵션
 * 
 */
com.initGridInfo = function(options){
	
	try{
		
		if ((typeof options !== "undefined") && (options!==null)){
			
			if ((typeof options.Main !== "undefined") && (options.Main!==null)){ //메인 그리드 세팅
				
				var gridInfo = {CUR_ROW_INDEX:-1, OLD_ROW_INDEX:-1, ERR_ROW_INDEX:-1, keyColumn:null, focusColumn:null
						, name:null, keyValue:null, dualSaving:false, undo:true};
				
				var main = options.Main;
				if ((typeof main.keyColumn !== "undefined") && (main.keyColumn!==null)){
					gridInfo.keyColumn = main.keyColumn;
				}
				if ((typeof main.focusColumn !== "undefined") && (main.focusColumn!==null)){
					gridInfo.focusColumn = main.focusColumn;
				}
				if ((typeof main.name !== "undefined") && (main.name!==null)){
					gridInfo.name = main.name;
				}
				if ((typeof main.undo !== "undefined") && (main.undo!==null)){
					gridInfo.undo = main.undo;
				}
				if ((typeof main.search_map !== "undefined") && (main.search_map!==null)){
					gridInfo.search_map = main.search_map;
				}				
				
				gcm.GRID_INFO[main.grid.org_id] = gridInfo;	
			}
			if ((typeof options.Sub1 !== "undefined") && (options.Sub1!==null)){ //서브 그리드1 세팅
				var gridInfo = {CUR_ROW_INDEX:-1, OLD_ROW_INDEX:-1, ERR_ROW_INDEX:-1, keyColumn:null, focusColumn:null
						, name:null, keyValue:null, dualSaving:false, undo:true};
				var sub1 = options.Sub1;
				if ((typeof sub1.keyColumn !== "undefined") && (sub1.keyColumn!==null)){
					gridInfo.keyColumn = sub1.keyColumn;
				}
				if ((typeof sub1.focusColumn !== "undefined") && (sub1.focusColumn!==null)){
					gridInfo.focusColumn = sub1.focusColumn;
				}
				if ((typeof sub1.name !== "undefined") && (sub1.name!==null)){
					gridInfo.name = sub1.name;
				}
				if ((typeof sub1.undo !== "undefined") && (sub1.undo!==null)){
					gridInfo.undo = sub1.undo;
				}
				if ((typeof sub1.search_map !== "undefined") && (sub1.search_map!==null)){
					gridInfo.search_map = sub1.search_map;
				}
				
				gcm.GRID_INFO[sub1.grid.org_id] = gridInfo;	
			}
			if ((typeof options.Sub2 !== "undefined") && (options.Sub2!==null)){ //서브 그리드1 세팅
				var gridInfo = {CUR_ROW_INDEX:-1, OLD_ROW_INDEX:-1, ERR_ROW_INDEX:-1, keyColumn:null, focusColumn:null
						, name:null, keyValue:null, dualSaving:false, undo:true};
				var sub2 = options.Sub2;
				if ((typeof sub2.keyColumn !== "undefined") && (sub2.keyColumn!==null)){
					gridInfo.keyColumn = sub2.keyColumn;
				}
				if ((typeof sub2.focusColumn !== "undefined") && (sub2.focusColumn!==null)){
					gridInfo.focusColumn = sub2.focusColumn;
				}
				if ((typeof sub2.name !== "undefined") && (sub2.name!==null)){
					gridInfo.name = sub2.name;
				}
				if ((typeof sub2.undo !== "undefined") && (sub2.undo!==null)){
					gridInfo.undo = sub2.undo;
				}
				if ((typeof sub2.search_map !== "undefined") && (sub2.search_map!==null)){
					gridInfo.search_map = sub2.search_map;
				}
				
				gcm.GRID_INFO[sub2.grid.org_id] = gridInfo;	
			}
		}	
	} catch (e) {
		
	}
}

/** 포커스된 grid정보 세팅함
 * 
 * @date 2021.03.13
 * @memberOf com
 * @author tracom
 * @param {Object} grid 대상 그리드
 * @param {Object} focusOption 포커스를 위한 옵션 정보
 * @param {int} curIndex 현재 index
 * @param {String} curKeyValue 현재 key값
 * @param {oldIndex} oldIndex 이전 index
 * @param {errIndex} errIndex 오류 index
 */
com.setGridInfo = function(grid,focusOption,curIndex,curKeyValue,oldIndex,errIndex){
	
	try{
		gridInfo = gcm.GRID_INFO[grid.org_id];
		
		gridInfo.saving=false;
		if ((typeof focusOption !== "undefined") && (focusOption!==null)){
			
			if ((typeof focusOption.keyColumn != "undefined") && (focusOption.keyColumn!=null)){
				gridInfo.keyColumn = focusOption.keyColumn;
			}
			
			if ((typeof focusOption.focusColumn != "undefined") && (focusOption.focusColumn!=null)){
				gridInfo.focusColumn = focusOption.focusColumn;
			}
		}
		
		if ((typeof curIndex != "undefined") && (curIndex!=null))gridInfo.CUR_ROW_INDEX = curIndex;
		if ((typeof curKeyValue != "undefined") && (curKeyValue!=null))gridInfo.keyValue = curKeyValue;
		if ((typeof oldIndex != "undefined") && (oldIndex!=null))gridInfo.OLD_ROW_INDEX = oldIndex;
		if ((typeof errIndex != "undefined") && (errIndex!=null))gridInfo.ERR_ROW_INDEX = errIndex;
	
	} catch (e) {
		
	}
}

/** 그리드의 key column명 및 value을 업데이트 함
 * 
 * @date 2021.03.13
 * @memberOf com
 * @author tracom
 * @param {Object} grid 대상 그리드
 * @param {String} mainGridNm main grid 명
 * @param {String} subGridNm1 sub grid1 명
 * @param {String} subGridNm2 sub grid2 명
 */
com.setNameGridInfo = function(grid,mainGridNm, subGridNm1, subGridNm2){
	try{
		gridInfo.mainGridNm = mainGridNm;
		gridInfo.subGridNm1 = subGridNm1;
		gridInfo.subGridNm2 = subGridNm2;
	} catch (e) {
		
	}
}

/** 그리드의 현재 index 및 현재 key 값 세팅
 * 
 * @date 2021.03.13
 * @memberOf com
 * @author tracom
 * @param {Object} grid 대상 그리드
 */
com.setKeyGridInfo = function(grid){
	try{
		gridInfo = gcm.GRID_INFO[grid.org_id];
		
		gridInfo.CUR_ROW_INDEX = grid.getFocusedRowIndex();
		
		var data = com.getGridViewDataList(grid);
		var curKeyValue = data.getCellData(gridInfo.CUR_ROW_INDEX, gridInfo.keyColumn); // 현재 포커스의 key Column의 값을 가져옴
		gridInfo.keyValue = curKeyValue;
	} catch (e) {
		
	}
}

/** 2개(main, sub)의 grid change시 처리 : 부모 그리드의 row 인덱스 위치가 바뀔때 마다 실행되며 프로그램코드에 해당하는 자식 그리드를 가져옴
 * 
 * @date 2021.03.13
 * @memberOf com
 * @author tracom
 * @param {Object} mainGrid 대상 main 그리드
 * @param {Object} subGrid 대상 sub 그리드
 * @param {Object} subSaveSbmObj sub grid 저장 submission 객체
 * @param {Object} subSrchSbmObj sub grid 검색 submission 객체
 * @param {Object} focusOption 포커스를 위한 옵션 정보
 * @param {int} row 위치 변경시 row index
 * @param {int} oldRow 이전 row index
 * @param {String} subNm 서브 grid 명
 */
com.changeDualGrid = function(mainGrid, subGrid, subSaveSbmObj, subSrchSbmObj, focusOption, row, oldRow, subNm) {	
	var mainData = com.getGridViewDataList(mainGrid);
	var subData = com.getGridViewDataList(subGrid);
	
	/*var mainData = com.getGridViewDataList(mainGrid);
	var subData;
	if (typeof subGrid == "string") {
		subGrid = $p.getComponentById(subGrid);
		subData = com.getGridViewDataList(subGrid);
	}*/
	var modifiedMainCnt = mainData.getModifiedIndex().length;
	var modifiedSubCnt = subData.getModifiedIndex().length;
	var curKeyValue = "";
	var curKeyValues = "";
	var keyColumn = "";
	try{		
		if ((typeof focusOption.keyMapColumn !== "undefined") && (focusOption.keyMapColumn!==null)){ //keyMap의 column명이 다를때...
			keyColumn = focusOption.keyMapColumn;
		}
		else{
			keyColumn = gcm.GRID_INFO[mainGrid.org_id].keyColumn;
		}
		var keyColumnArr = keyColumn.split(',');

		for (var i = 0; i < keyColumnArr.length; i++) {
			curKeyValue = mainData.getCellData(row, keyColumnArr[i]);
			focusOption.keyMap.set(keyColumnArr[i], curKeyValue);
			
			if(i!=0)curKeyValues += ",";
			curKeyValues += (curKeyValue);
		}

		subNm = gcm.GRID_INFO[subGrid.org_id].name;
	} catch (e) {
		
	}

	com.setGridInfo(mainGrid, focusOption,row, curKeyValues, oldRow);

	if (modifiedSubCnt > 0) {
		if(gcm.GRID_INFO[mainGrid.org_id].dualSaving){ //메인/서브 그리드 저장중에는 저장여부 팝업 출력 되지 않도록 함
			if (modifiedSubCnt > 0) {
				com.saveData(subGrid,null,subSaveSbmObj);
			} else {
				com.searchGrid(subGrid,subSrchSbmObj);
			}
		}
		else {
			com.confirm(subNm + modifiedSubCnt + " 건의 저장하지 않은 데이터가 있습니다. 저장 하시겠습니까?", function(rtn){
				if (rtn) {
					if (modifiedSubCnt > 0) {
						com.saveData(subGrid,null,subSaveSbmObj);
					} else {
						
						com.searchGrid(subGrid,subSrchSbmObj);
					}
				} else {
					//var subFocusedValue = mainData.getCellData(mainGrid.getFocusedRowIndex(), keyColumn);
					if (curKeyValue) {
						
						com.searchGrid(subGrid,subSrchSbmObj);
					}
				}
			});
		}
	} else {
		var rowStatus = mainData.getRowStatus(row);

		if (rowStatus == "C") {
			subData.removeAll();
		} else {
			if (curKeyValue) {
				com.searchGrid(subGrid,subSrchSbmObj);
			}
		}
	}
	gcm.GRID_INFO[mainGrid.org_id].dualSaving = false;
};

/** 2개(main, sub)의 grid change시 처리 : sub grid 수정사항을 저장하지 않을때 main grid를 초기화 함
 * 
 * @date 2021.03.13
 * @memberOf com
 * @author tracom
 * @param {Object} mainGrid 대상 main 그리드
 * @param {Object} subGrid 대상 sub 그리드
 * @param {Object} subSaveSbmObj sub grid 저장 submission 객체
 * @param {Object} subSrchSbmObj sub grid 검색 submission 객체
 * @param {Object} focusOption 포커스를 위한 옵션 정보
 * @param {int} row 위치 변경시 row index
 * @param {int} oldRow 이전 row index
 * @param {String} subNm 서브 grid 명
 */
com.changeDualGrid2 = function(mainGrid, subGrid, subSaveSbmObj, subSrchSbmObj, focusOption, row, oldRow, subNm) {	
	var mainData = com.getGridViewDataList(mainGrid);
	var subData = com.getGridViewDataList(subGrid);
	
	/*var mainData = com.getGridViewDataList(mainGrid);
	var subData;
	if (typeof subGrid == "string") {
		subGrid = $p.getComponentById(subGrid);
		subData = com.getGridViewDataList(subGrid);
	}*/
	var modifiedMainCnt = mainData.getModifiedIndex().length;
	var modifiedSubCnt = subData.getModifiedIndex().length;
	var curKeyValue = "";
	var curKeyValues = "";
	var keyColumn = "";
	try{		
		if ((typeof focusOption.keyMapColumn !== "undefined") && (focusOption.keyMapColumn!==null)){ //keyMap의 column명이 다를때...
			keyColumn = focusOption.keyMapColumn;
		}
		else{
			keyColumn = gcm.GRID_INFO[mainGrid.org_id].keyColumn;
		}
		var keyColumnArr = keyColumn.split(',');

		for (var i = 0; i < keyColumnArr.length; i++) {
			curKeyValue = mainData.getCellData(row, keyColumnArr[i]);
			focusOption.keyMap.set(keyColumnArr[i], curKeyValue);
			
			if(i!=0)curKeyValues += ",";
			curKeyValues += (curKeyValue);
		}

		subNm = gcm.GRID_INFO[subGrid.org_id].name;
	} catch (e) {
		
	}

	com.setGridInfo(mainGrid, focusOption,row, curKeyValues, oldRow);

	if (modifiedSubCnt > 0) {
		if(gcm.GRID_INFO[mainGrid.org_id].dualSaving){ //메인/서브 그리드 저장중에는 저장여부 팝업 출력 되지 않도록 함
			if (modifiedSubCnt > 0) {
				com.saveData(subGrid,null,subSaveSbmObj);
			} else {
				com.searchGrid(subGrid,subSrchSbmObj);
			}
		}
		else {
			com.confirm(subNm + modifiedSubCnt + " 건의 저장하지 않은 데이터가 있습니다. 저장 하시겠습니까?", function(rtn){
				if (rtn) {
					if (modifiedSubCnt > 0) {
						com.saveData(subGrid,null,subSaveSbmObj);
					} else {
						com.clearGrid(mainGrid);
						com.searchGrid(subGrid,subSrchSbmObj);
					}
				} else {
					//var subFocusedValue = mainData.getCellData(mainGrid.getFocusedRowIndex(), keyColumn);
					if (curKeyValue) {
						com.clearGrid(mainGrid);
						com.searchGrid(subGrid,subSrchSbmObj);
					}
				}
			});
		}
	} else {
		var rowStatus = mainData.getRowStatus(row);

		if (rowStatus == "C") {
			subData.removeAll();
		} else {
			if (curKeyValue) {
				com.searchGrid(subGrid,subSrchSbmObj);
			}
		}
	}
	gcm.GRID_INFO[mainGrid.org_id].dualSaving = false;
};

/** 3개(main, sub1, sub2)의 grid change시 처리 : 부모 그리드의 row 인덱스 위치가 바뀔때 마다 실행되며 프로그램코드에 해당하는 자식 그리드를 가져옴
 * 
 * @date 2021.03.13
 * @memberOf com
 * @author tracom
 * @param {Object} mainGrid 대상 main 그리드
 * @param {Object} subGrid1 대상 sub1 그리드
 * @param {Object} subGrid2 대상 sub2 그리드
 * @param {Object} subSaveSbmObj1 sub1 grid 저장 submission 객체
 * @param {Object} subSaveSbmObj2 sub2 grid 저장 submission 객체
 * @param {Object} subSrchSbmObj1 sub1 grid 검색 submission 객체
 * @param {Object} subSrchSbmObj2 sub2 grid 검색 submission 객체
 * @param {Object} focusOption 포커스를 위한 옵션 정보
 * @param {int} row 위치 변경시 row index
 * @param {int} oldRow 이전 row index
 * @param {String} subNm 서브 grid 명
 */
com.changeThirdGrid = function(mainGrid, subGrid1, subGrid2, subSaveSbmObj1, subSaveSbmObj2, subSrchSbmObj1, subSrchSbmObj2, focusOption, row, oldRow, subNm) {	
	
	
	var mainData = com.getGridViewDataList(mainGrid);
	var subData1 = com.getGridViewDataList(subGrid1);
	var subData2 = com.getGridViewDataList(subGrid2);
	
	var modifiedMainCnt = mainData.getModifiedIndex().length;
	var modifiedSubCnt1 = subData1.getModifiedIndex().length;
	var modifiedSubCnt2 = subData2.getModifiedIndex().length;
	
	var curKeyValues = "";
	var curKeyValue = "";
	var keyColumn = "";
	try{
		//curKeyValue = mainData.getCellData(row, gcm.GRID_INFO[mainGrid.org_id].keyColumn);
		
		if ((typeof focusOption.keyMapColumn !== "undefined") && (focusOption.keyMapColumn!==null)){ //keyMap의 column명이 다를때...
			keyColumn = focusOption.keyMapColumn;
		}
		else{
			keyColumn = gcm.GRID_INFO[mainGrid.org_id].keyColumn;
		}
		//focusOption.keyMap.set(keyColumn, curKeyValue);
		var keyColumnArr = keyColumn.split(',');

		
		for (var i = 0; i < keyColumnArr.length; i++) {
			curKeyValue = mainData.getCellData(row, keyColumnArr[i]);
			focusOption.keyMap.set(keyColumnArr[i], curKeyValue);
			
			if(i!=0)curKeyValues += ",";
			curKeyValues += (curKeyValue);
		}
	} catch (e) {
		
	}

	com.setGridInfo(mainGrid, focusOption,row, curKeyValues, oldRow);

	if (modifiedSubCnt1 > 0 || modifiedSubCnt2 > 0) {
		if(gcm.GRID_INFO[mainGrid.org_id].dualSaving){ //메인/서브 그리드 저장중에는 저장여부 팝업 출력 되지 않도록 함
			if (modifiedSubCnt1 > 0) {
				com.saveData(subGrid1,null,subSaveSbmObj1);
			}
			if (modifiedSubCnt2 > 0) {
				com.saveData(subGrid2,null,subSaveSbmObj2);
			}
		}
		else {
			var confirmStr = com.strModifiedCnt2([subGrid1, subGrid2]);
			com.confirm(confirmStr + " 의 저장하지 않은 데이터가 있습니다. 저장 하시겠습니까?", function(rtn){
				if (rtn) {
					if (modifiedSubCnt1 > 0) {
						com.saveData(subGrid1,null,subSaveSbmObj1);
					}
					if (modifiedSubCnt2 > 0) {
						com.saveData(subGrid2,null,subSaveSbmObj2);
					}
				} else {
					//var subFocusedValue = mainData.getCellData(mainGrid.getFocusedRowIndex(), keyColumn);
					if (curKeyValue) {
						com.searchGrid(subGrid1,subSrchSbmObj1);
						com.searchGrid(subGrid2,subSrchSbmObj2);
					}
				}
			});
		}
	} else {
		var rowStatus = mainData.getRowStatus(row);

		if (rowStatus == "C") {
			subData1.removeAll();
			subData2.removeAll();
		} else {
			if (curKeyValue) {
				com.searchGrid(subGrid1,subSrchSbmObj1);
				com.searchGrid(subGrid2,subSrchSbmObj2);
			}
		}
	}
	gcm.GRID_INFO[mainGrid.org_id].dualSaving = false;
};

/*com.changeThirdGrid2 = function(mainGrid, subGrid1, subGrid2, subSrchSbmObj1, subSrchSbmObj2, focusOption, row, oldRow, subNm, subSaveSbmObj1, subSaveSbmObj2) {	
	
	
	var mainData = com.getGridViewDataList(mainGrid);
	var subData1 = com.getGridViewDataList(subGrid1);
	var subData2 = com.getGridViewDataList(subGrid2);
	
	var modifiedMainCnt = mainData.getModifiedIndex().length;
	var modifiedSubCnt1 = subData1.getModifiedIndex().length;
	var modifiedSubCnt2 = subData2.getModifiedIndex().length;
	
	var curKeyValue = "";
	var curKeyValues = "";
	var keyColumn = "";
	try{
		curKeyValue = mainData.getCellData(row, gcm.GRID_INFO[mainGrid.org_id].keyColumn);
		
		if ((typeof focusOption.keyMapColumn !== "undefined") && (focusOption.keyMapColumn!==null)){ //keyMap의 column명이 다를때...
			keyColumn = focusOption.keyMapColumn;
		}
		else{
			keyColumn = gcm.GRID_INFO[mainGrid.org_id].keyColumn;
		}
		//focusOption.keyMap.set(keyColumn, curKeyValue);
		var keyColumnArr = keyColumn.split(',');

		for (var i = 0; i < keyColumnArr.length; i++) {
			curKeyValue = mainData.getCellData(row, keyColumnArr[i]);
			focusOption.keyMap.set(keyColumnArr[i], curKeyValue);
			
			if(i!=0)curKeyValues += ",";
			curKeyValues += (curKeyValue);
		}
	} catch (e) {
		
	}
	
	com.setGridInfo(mainGrid, focusOption,row, curKeyValues, oldRow);
	
	if (modifiedSubCnt1 > 0 || modifiedSubCnt2 > 0) {
		if(gcm.GRID_INFO[mainGrid.org_id].dualSaving){ //메인/서브 그리드 저장중에는 저장여부 팝업 출력 되지 않도록 함
			if (modifiedSubCnt1 > 0) {
				com.saveData(subGrid1,null,subSaveSbmObj1);
			}
			if (modifiedSubCnt2 > 0) {
				com.saveData(subGrid2,null,subSaveSbmObj2);
			}
		}
		else {
			var confirmStr = com.strModifiedCnt2([subGrid1, subGrid2]);
			com.confirm(confirmStr + " 의 저장하지 않은 데이터가 있습니다. 저장 하시겠습니까?", function(rtn){
				if (rtn) {
					if (modifiedSubCnt1 > 0) {
						com.saveData(subGrid1,null,subSaveSbmObj1);
					}
					if (modifiedSubCnt2 > 0) {
						com.saveData(subGrid2,null,subSaveSbmObj2);
					}
				} else {
					//var subFocusedValue = mainData.getCellData(mainGrid.getFocusedRowIndex(), keyColumn);
					if (curKeyValue) {
						com.searchGrid(subGrid1,subSrchSbmObj1);
						com.searchGrid(subGrid2,subSrchSbmObj2);
					}
				}
			});
		}
	} else {
		var rowStatus = mainData.getRowStatus(row);
		
		if (rowStatus == "C") {
			subData1.removeAll();
			subData2.removeAll();
		} else {
			if (curKeyValue) {
				com.searchGrid(subGrid1,subSrchSbmObj1);
				com.searchGrid(subGrid2,subSrchSbmObj2);
			}
		}
	}
	gcm.GRID_INFO[mainGrid.org_id].dualSaving = false;
};*/

/** 1개의 grid change시 처리
 * 
 * @date 2021.03.13
 * @memberOf com
 * @author tracom
 * @param {Object} mainGrid 대상 main 그리드
 * @param {int} row 위치 변경시 row index
 * @param {int} oldRow 이전 row index
 */
com.changeSingleGrid = function(mainGrid, row, oldRow) {
	
	var curKeyValue = "";
	var curKeyValues = "";
	try{
		var mainData = com.getGridViewDataList(mainGrid);
		var keyColumn = gcm.GRID_INFO[mainGrid.org_id].keyColumn;
		var keyColumnArr = keyColumn.split(',');

		for (var i = 0; i < keyColumnArr.length; i++) {
			curKeyValue = mainData.getCellData(row, keyColumnArr[i]);
			//focusOption.keyMap.set(keyColumnArr[i], curKeyValue);
			
			if(i!=0)curKeyValues += ",";
			curKeyValues += (curKeyValue);
		}
		com.setGridInfo(mainGrid, null,row, curKeyValues, oldRow);
	} catch (e) {
		
	}
};

/*사용안함*/
com.insertMappingGrid = function(targetGrid, sourceObj, pKeyMap, pKey, key){
	var data = com.getGridViewDataList(targetGrid);
	var dLength = obj.length;
	for (i = 0; i < dLength; i++) {
		var data = data.getMatchedIndex(key, eval("sourceObj[i]."+key), true);
		if (data.length == 0) {
			eval("obj[i]."+pKey) = pKeyMap.get(pKey);;
			obj[i].chk = "0";
			var idx = data.insertRow();
			data.setRowJSON(idx, obj[i], true);
		}
	}
}

/** 특정 grid 값을 제외한 grid 추출
 * 
 * @date 2021.03.13
 * @memberOf com
 * @author tracom
 * @param {Object} grid 대상 그리드
 * @param {Object} items 제외할 grid 데이터를 가지고 있는 item 배열
 * @param {String} matchedColumn 매칭할 column
 */
com.excludeItemsByGrid = function(grid, items, matchedColumn){
	var data = com.getGridViewDataList(grid);
	for (i = 0; i < items.length; i++) {
		var curIndex = data.getMatchedIndex(matchedColumn, items[i]);
		data.removeRow(curIndex);
	}
}

var shortcutTargetElement = document;
if (shortcutTargetElement.attachEvent) {
	shortcutTargetElement.detachEvent("keydown", gcm.shortcutEvent.keydownEvent);
	shortcutTargetElement.attachEvent("keydown", gcm.shortcutEvent.keydownEvent);
} else {
	shortcutTargetElement.onkeydown = gcm.shortcutEvent.keydownEvent;
}



/** 사용안함
 * 실행 프레임 정보 조회
 * 
 * @date 2021.05.17
 * @memberOf com
 * @author 양현우
 * @param {Array} options Popup창 옵션
 * @param {String} [options.id] Popup창 ID
 * @param {String} [options.type] 화면 오픈 타입 ("iframePopup", "wframePopup", "browserPopup")
 * @param {String} [options.width] Popup창 넓이
 * @param {String} [options.height] Popup창 높이
 * @param {String} [options.popupName] useIframe : true시 popup 객체의 이름으로 popup 프레임의 표시줄에 나타납니다.
 * @param {String} [options.useIFrame] [default : false] true : IFrame 을 사용하는 WebSquare popup / false: window.open 을 사용하는 popup
 * @param {String} [options.style] Popup의 스타일을 지정합니다. 값이 있으면 left top width height는 적용되지 않습니다.
 * @param {String} [options.resizable] [default : false]
 * @param {String} [options.modal] [default : false]
 * @param {String} [options.scrollbars] [default : false]
 * @param {String} [options.title] [default : false]
 * @param {String} [options.notMinSize] [default : false]
 */

com.searchPopup = function(url, opt, column, data) {
	
	var _dataObj = {
			type : "json",
			data : data,
			name : "param"
		};

		var width = opt.width || 1000;
		var height = opt.height || 500;
		try {
			var deviceWidth = parseFloat($("body").css("width"));
			var deviceHeight = parseFloat($("body").css("height"));
			if (!opt.notMinSize) {
				if (deviceWidth > 0 && width > deviceWidth) {
					width = deviceWidth - 4; // 팝업 border 고려
				}

				if (deviceHeight > 0 && height > deviceHeight) {
					height = deviceHeight - 4; // 팝업 border 고려
				}
			}
		} catch (e) {

		}

		var top = ((document.body.offsetHeight / 2) - (parseInt(height) / 2) + $(document).scrollTop()) + "px";
		var left = ((document.body.offsetWidth / 2) - (parseInt(width) / 2) + $(document).scrollLeft()) + "px";

		if (typeof _dataObj.data.callbackFn === "undefined") {
			_dataObj.data.callbackFn = "";
		} else if (_dataObj.data.callbackFn.indexOf("gcm") !== 0) {
			_dataObj.data.callbackFn = $p.id + _dataObj.data.callbackFn;
		}

		var options = {
			id : opt.id,
			popupName : opt.popupName || "",
			type : opt.type || "wframePopup",
			width : width + "px",
			height : height + "px",
			top : opt.top || top || "140px",
			left : opt.left || left || "500px",
			modal : (opt.modal == false) ? false : true,
			dataObject : _dataObj,
			alwaysOnTop : opt.alwaysOnTop || false,
			useModalStack : (opt.useModalStack == false) ? false : true,
			resizable : (opt.resizable == false) ? false : true,
			useMaximize : opt.useMaximize || false
		};

		$p.openPopup(url, options);	
	
}

/** xss decoding
 * 
 * @date 2021.03.13
 * @memberOf com
 * @author tracom
 * @param {String} str 대상 문구
 */
com.decodeXss = function(str) {
	return str.replaceAll("&#40;", "(").replaceAll("&#41;", ")");
}

/** 노선의 노드 순번을 그리드 순서대로 재 할당함.
 * 
 * @date 2021.03.13
 * @memberOf com
 * @author tracom
 * @param {Object} grid 대상 그리드
 * @param {String} column 대상 column 명
 */
com.setSerialNumberToData= function(grid, column){
	var data = com.getGridViewDataList(grid);
	var rowData = data.getAllJSON();
	var nodeSn = 0;
	for(var i = 0; i < rowData.length; i++) { //노선의 노드 순번을 그리드 순서대로 재 할당함.
		if(data.getRowStatus(i)!="D"){
			nodeSn ++;
			var oldNodeSn = data.getCellData(i,column);
			if(oldNodeSn != nodeSn){
				//data.setCellData(i,oldColumn, oldNodeSn);
				data.setCellData(i,column, nodeSn);
			}
		}
	}
	return nodeSn;
}

/** 문구가 비었는지 확인
 * 
 * @date 2021.03.13
 * @memberOf com
 * @author tracom
 * @param {String} str 대상 문구
 */
com.isEmpty = function(str){
	return str == ''||str == null||typeof str == "undefined";
}

/* 사용 안함 */
com.loadingBar = function(isShowLoadinBar){
	if(com.isEmpty(isShowLoadinBar))
	{
		isShowLoadinBar = false;
	}
	if(isShowLoadinBar)
	{
		try
		{
			$.blockUI({
		    	message : "<img src='"+ "/cm/images/inc/progressingbar.gif'/>"
			   	,css:
			   	{ 
					 top:  ($(window).height() - 100) / 2 + 'px' 
					,left: ($(window).width()  - 100) / 2 + 'px' 
					,width: '100px'
		        }
			   ,overlayCSS:
			   {
				   backgroundColor: 'transparent'
			   }
			});
		}
		catch(e)
		{
			$p.log(e);
		}
	}
	else{
		$.unblockUI();
	}
}

/** 특정 column의 값보다 큰 index 찾음
 * 
 * @date 2021.03.13
 * @memberOf com
 * @author tracom
 * @param {Object} data 대상 그리드의 data
 * @param {String} column 대상 column 명
 * @param {int} value 비교할 column의 값
 * @return {int} 특정 column의 값보다 큰 index
 */
com.indexGridNear = function(data, column, value){
	var i = 0;
	for(i = 0; i < data.getRowCount(); i++) {
		if(data.getRowStatus(i)!="D"){
			if(data.getCellData(i,column) > value){
				return i;
			}
		}
	}
	return i;
}

/** grid에서 검색하여 해당 값이 있는 위치로 포커스 이용
 * 
 * @date 2021.03.13
 * @memberOf com
 * @author tracom
 * @param {Object} grid 대상 그리드
 * @param {String} searchColumn 검색할 column 명
 * @param {int} content 검색어
 * @return {int} index 현재 index
 */
com.gridMove = function(grid,searchColumn, content, index){
	var data = com.getGridViewDataList(grid);
	var rowData = data.getAllJSON();
	var startIndex = 0;
	var keyColumnArr = searchColumn.split(',');
	
	if(index<rowData.length-1){ //현재 포커스 다음 index 부터 검색
		startIndex = index+1;
	}
	var nodeSn = 0;
	for(var i = startIndex; i < rowData.length; i++) { //현재 포커스 다음 index 부터 마지막까지 검색
		if(data.getRowStatus(i)!="D"){
			for(var j = 0; j < keyColumnArr.length; j++){
				if(data.getCellData(i,keyColumnArr[j]).indexOf(content)>=0){
					grid.setFocusedCell(i, keyColumnArr[j]);
					return;
				}
			}
		}
	}
	
	for(var i = 0; i < startIndex; i++) { //0 부터 현재 포커스 index 까지 검색
		if(data.getRowStatus(i)!="D"){
			for(var j = 0; j < keyColumnArr.length; j++){
				if(data.getCellData(i,keyColumnArr[j]).indexOf(content)>=0){
					grid.setFocusedCell(i, keyColumnArr[j]);
					return;
				}
			}
		}
	}
}

/** 
 * 
 * @date 2022.03.11
 * @memberOf com
 * @author tracom
 * @param {Object} grid 대상 그리드
 * @param {Object} saveSbmObj 저장 submission 객체
 * @param {String} str 저장 동작시 확인여부 문구
 */
com.checkGridSave = function(grid,saveSbmObj,str){
	
	if(	(typeof saveSbmObj !== "undefined")&&(saveSbmObj !== null)){
		var idx = grid.getModifiedIndex().length;
		
		//저장하지 않고 조회할 경우
		if(idx > 0) {
			if(	(typeof str == "undefined") || (str.trim() == "")){
				str = com.strModifiedCnt(grid) + "의 저장하지 않은 데이터가 있습니다. 저장 후 조회 하시겠습니까?";
			}
			else {
				str = idx + str;
			}
			com.confirm(str, function(rtn){
				if (rtn) {
					com.saveData(grid, saveSbmObj)
				}
				else {
					com.clearGrid(grid);
				}
				return;
			});
		} 
		else {
			com.clearGrid(grid);
		}
	}
	else {
		//com.clearGrid(grid);
	}
}