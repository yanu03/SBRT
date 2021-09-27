package kr.tracom.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class CommonUtil {

	/**
	 * 
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(CommonUtil.class);

	/**
	 * 작성자: 트라콤
	 * 작성일: 2016. 1. 6.
	 * 수정일: 2016. 1. 6.
	 * 목적 : 호스트명 취득
	 * 
	 * @return
	 */
	public static String getHostName()
	{
		String hostname = "";
		Process process = null;

		InputStreamReader isr = null;
		BufferedReader br = null;

		try
		{
			process = Runtime.getRuntime().exec("hostname");
			isr = new InputStreamReader(process.getInputStream());
			br = new BufferedReader(isr);
			hostname = br.readLine();
		}
		catch(Exception e)
		{
			hostname = "unKnown";
			return hostname;
		}
		finally
		{
			if (br != null)
			{
				try
				{
					br.close();
				}
				catch(IOException e)
				{
					unwrapThrowable(e);
				}
			}

			if (isr != null)
			{
				try
				{
					isr.close();
				}
				catch(IOException e)
				{
					unwrapThrowable(e);
				}
			}
		}

		return hostname;
	}

	/**
	 * 작성자: 트라콤
	 * 작성일: 2016. 1. 6.
	 * 수정일: 2016. 1. 6.
	 * 목적 : 예외 로깅
	 */
	public static void unwrapThrowable(Throwable ex)
	{
		String errorMessage = ex.getMessage();

		StackTraceElement[] traces = ex.getStackTrace();

		String classInfoMessage = "";

		for (StackTraceElement trace : traces)
		{
			if (trace.getLineNumber() > 0)
			{
				classInfoMessage = "\tat " + trace.getClassName();
				classInfoMessage += "." + trace.getMethodName();
				classInfoMessage += "(" + trace.getFileName();
				classInfoMessage += ":" + trace.getLineNumber() + ")";

				LOGGER.error(classInfoMessage);
			}
		}

		LOGGER.error(errorMessage);
	}

	
	public static void main(String[] args)
	{
		System.out.println(CommonUtil.encodeXSS("' script는 지양하셔야 합니다. iframe은 사용하시면 안됩니다! onload"));
	}
	/**
	 * 작성자: 트라콤
	 * 작성일: 2021. 5. 7.
	 * 수정일: 2021. 5. 7.
	 * 목적 : 웹방어 변조
	 * 
	 * @param strValue
	 * // * @return 변조된 문자
	 */
	public static String encodeXSS(String strValue)
	{
		String value = strValue;
		value = value.replaceAll("'", "&#39;");
		value = value.replaceAll("\\(", "&#40;");
		value = value.replaceAll("\\)", "&#41;");
		value = value.replaceAll("--", "&#45;&#45;");
		value = value.replaceAll("eval\\((.*)\\)", "");
		value = value.replaceAll("[\\\"\\\'][\\s]*javascript:(.*, arg1)[\\\"\\\']", "\"\"");
		value = value.replaceAll("<", "&lt;");
		value = value.replaceAll(">", "&gt;");
		value = value.replaceAll("\"", "&quot;");
		value = value.replaceAll("\"", "&#34;");
		value = value.replaceAll("(?i)script", "&#115;cript");
		value = value.replaceAll("(?i)iframe", "i-frame");
		value = value.replaceAll("(?i)frameset", "frame-set");
		value = value.replaceAll("(?i)applet", "apple&#116;");
		value = value.replaceAll("(?i)javascript", "java-script");
		value = value.replaceAll("(?i)vbscript", "vb-script");
		value = value.replaceAll("(?i)onactivate", "on-activate");
		value = value.replaceAll("(?i)on-abort", "onabort");
		value = value.replaceAll("(?i)onafterprint", "on-afterprint");
		value = value.replaceAll("(?i)onafterupdate", "on-afterupdate");
		value = value.replaceAll("(?i)onbeforeactivate", "on-beforeactivate");
		value = value.replaceAll("(?i)onbeforecopy", "on-beforecopy");
		value = value.replaceAll("(?i)onbeforecut", "on-beforecut");
		value = value.replaceAll("(?i)onbeforedeactivate", "on-beforedeactivate");
		value = value.replaceAll("(?i)onbeforeeditfocus", "on-beforeeditfocus");
		value = value.replaceAll("(?i)onbeforepaste", "on-beforepaste");
		value = value.replaceAll("(?i)onbeforeunload", "on-beforeunload");
		value = value.replaceAll("(?i)onbeforeupdate", "on-beforeupdate");
		value = value.replaceAll("(?i)onblur", "on-blur");
		value = value.replaceAll("(?i)onbounce", "on-bounce");
		value = value.replaceAll("(?i)onbegin", "on-begin");
		value = value.replaceAll("(?i)oncanplay", "on-canplay");
		value = value.replaceAll("(?i)oncellchange", "on-cellchange");
		value = value.replaceAll("(?i)onchange", "on-change");
		value = value.replaceAll("(?i)onclick", "on-click");
		value = value.replaceAll("(?i)oncontextmenu", "on-contextmenu");
		value = value.replaceAll("(?i)oncontrolselect", "on-controlselect");
		value = value.replaceAll("(?i)oncopy", "on-copy");
		value = value.replaceAll("(?i)oncut", "on-cut");
		value = value.replaceAll("(?i)oncontentready", "on-contentready");
		value = value.replaceAll("(?i)oncontentsave", "on-contentsave");
		value = value.replaceAll("(?i)ondataavailable", "on-dataavailable");
		value = value.replaceAll("(?i)ondatasetchanged", "on-datasetchanged");
		value = value.replaceAll("(?i)ondatasetcomplete", "on-datasetcomplete");
		value = value.replaceAll("(?i)ondblclick", "on-dblclick");
		value = value.replaceAll("(?i)ondeactivate", "on-deactivate");
		value = value.replaceAll("(?i)ondetach", "on-detach");
		value = value.replaceAll("(?i)ondocumentready", "on-documentready");
		value = value.replaceAll("(?i)ondrag", "on-drag");
		value = value.replaceAll("(?i)ondragend", "on-dragend");
		value = value.replaceAll("(?i)ondragenter", "on-dragenter");
		value = value.replaceAll("(?i)ondragleave", "on-dragleave");
		value = value.replaceAll("(?i)ondragover", "on-dragover");
		value = value.replaceAll("(?i)ondragstart", "on-dragstart");
		value = value.replaceAll("(?i)ondragdrop", "on-dragdrop");
		value = value.replaceAll("(?i)ondrop", "on-drop");
		value = value.replaceAll("(?i)ondurationchange", "on-durationchange");
		value = value.replaceAll("(?i)onemptied", "on-emptied");
		value = value.replaceAll("(?i)onended", "on-ended");
		value = value.replaceAll("(?i)onerrorupdate", "on-errorupdate");
		value = value.replaceAll("(?i)onfilterchange", "on-filterchange");
		value = value.replaceAll("(?i)onfinish", "on-finish");
		value = value.replaceAll("(?i)onfocus", "on-focus");
		value = value.replaceAll("(?i)onfocusin", "on-focusin");
		value = value.replaceAll("(?i)onfocusout", "on-focusout");
		value = value.replaceAll("(?i)onhashchange", "on-hashchange");
		value = value.replaceAll("(?i)onhelp", "on-help");
		value = value.replaceAll("(?i)onhide", "on-hide");
		value = value.replaceAll("(?i)oninput", "on-input");
		value = value.replaceAll("(?i)onkeydown", "on-keydown");
		value = value.replaceAll("(?i)onkeypress", "on-keypress");
		value = value.replaceAll("(?i)onkeyup", "on-keyup");
		value = value.replaceAll("(?i)onlayoutcomplete", "on-layoutcomplete");
		value = value.replaceAll("(?i)onload", "on-load");
		value = value.replaceAll("(?i)onloadeddata", "on-loadeddata");
		value = value.replaceAll("(?i)onloadedmetadata", "on-loadedmetadata");
		value = value.replaceAll("(?i)onloadstart", "on-loadstart");
		value = value.replaceAll("(?i)onlosecapture", "on-losecapture");
		value = value.replaceAll("(?i)onmessage", "on-message");
		value = value.replaceAll("(?i)onmediacomplete", "on-mediacomplete");
		value = value.replaceAll("(?i)onmediaerror", "on-mediaerror");
		value = value.replaceAll("(?i)onmedialoadfailed", "on-medialoadfailed");
		value = value.replaceAll("(?i)onmousedown", "on-mousedown");
		value = value.replaceAll("(?i)onmouseenter", "on-mouseenter");
		value = value.replaceAll("(?i)onmouseleave", "on-mouseleave");
		value = value.replaceAll("(?i)onmousemove", "on-mousemove");
		value = value.replaceAll("(?i)onmouseout", "on-mouseout");
		value = value.replaceAll("(?i)onmouseover", "on-mouseover");
		value = value.replaceAll("(?i)onmouseup", "on-mouseup");
		value = value.replaceAll("(?i)onmousewheel", "on-mousewheel");
		value = value.replaceAll("(?i)onmove", "on-move");
		value = value.replaceAll("(?i)onmoveend", "on-moveend");
		value = value.replaceAll("(?i)onmovestart", "on-movestart");
		value = value.replaceAll("(?i)onoffline", "on-offline");
		value = value.replaceAll("(?i)ononline", "on-online");
		value = value.replaceAll("(?i)ononopenstatechange", "on-openstatechange");
		value = value.replaceAll("(?i)ononoutofsync", "on-outofsync");
		value = value.replaceAll("(?i)onpage", "on-page");
		value = value.replaceAll("(?i)onerror", "on-error");
		value = value.replaceAll("(?i)onpaste", "on-paste");
		value = value.replaceAll("(?i)onpause", "on-pause");
		value = value.replaceAll("(?i)onplay", "on-play");
		value = value.replaceAll("(?i)onplaying", "on-playing");
		value = value.replaceAll("(?i)onplaystatechange", "on-playstatechange");
		value = value.replaceAll("(?i)onprogress", "on-progress");
		value = value.replaceAll("(?i)onratechange", "on-ratechange");
		value = value.replaceAll("(?i)onpropertychange", "on-propertychange");
		value = value.replaceAll("(?i)onreadystatechange", "on-readystatechange");
		value = value.replaceAll("(?i)onrepeat", "on-repeat");
		value = value.replaceAll("(?i)onresume", "on-resume");
		value = value.replaceAll("(?i)onreset", "on-reset");
		value = value.replaceAll("(?i)onresize", "on-resize");
		value = value.replaceAll("(?i)onresizeend", "on-resizeend");
		value = value.replaceAll("(?i)onresizestart", "on-resizestart");
		value = value.replaceAll("(?i)onreverse", "on-reverse");
		value = value.replaceAll("(?i)onrowclick", "on-rowclick");
		value = value.replaceAll("(?i)onrowout", "on-rowout");
		value = value.replaceAll("(?i)onrowenter", "on-rowenter");
		value = value.replaceAll("(?i)onrowover", "on-rowover");
		value = value.replaceAll("(?i)onrowdelete", "on-rowdelete");
		value = value.replaceAll("(?i)onrowexit", "on-rowexit");
		value = value.replaceAll("(?i)onrowsdelete", "on-rowsdelete");
		value = value.replaceAll("(?i)onrowsinserted", "on-rowsinserted");
		value = value.replaceAll("(?i)onsave", "on-save");
		value = value.replaceAll("(?i)onseek", "on-seek");
		value = value.replaceAll("(?i)onscroll", "on-scroll");
		value = value.replaceAll("(?i)onseeked", "on-seeked");
		value = value.replaceAll("(?i)onseeking", "on-seeking");
		value = value.replaceAll("(?i)onselect", "on-select");
		value = value.replaceAll("(?i)onselectionchange", "on-selectionchange");
		value = value.replaceAll("(?i)onselectstart", "on-selectstart");
		value = value.replaceAll("(?i)onshow", "on-show");
		value = value.replaceAll("(?i)onstalled", "on-stalled");
		value = value.replaceAll("(?i)onstart", "on-start");
		value = value.replaceAll("(?i)onstop", "on-stop");
		value = value.replaceAll("(?i)onstorage", "on-storage");
		value = value.replaceAll("(?i)onstoragecommit", "on-storagecommit");
		value = value.replaceAll("(?i)onsubmit", "on-submit");
		value = value.replaceAll("(?i)onsuspend", "on-suspend");
		value = value.replaceAll("(?i)onsyncrestored", "on-syncrestored");
		value = value.replaceAll("(?i)ontimeerror", "on-timeerror");
		value = value.replaceAll("(?i)ontimeout", "on-timeout");
		value = value.replaceAll("(?i)ontimeupdate", "on-timeupdate");
		value = value.replaceAll("(?i)ontrackchange", "on-trackchange");
		value = value.replaceAll("(?i)onunload", "on-unload");
		value = value.replaceAll("(?i)onurlflip", "on-urlflip");
		value = value.replaceAll("(?i)onvolumechange", "on-volumechange");
		value = value.replaceAll("(?i)onwaiting", "on-waiting");

		return value;
	}
	public static String encodeXSS2(String strValue)
	{
		String value = strValue;
		value = value.replaceAll("'", "&#39;");
		value = value.replaceAll("\\(", "&#40;");
		value = value.replaceAll("\\)", "&#41;");
		value = value.replaceAll("--", "&#45;&#45;");
		value = value.replaceAll("eval\\((.*)\\)", "");
		value = value.replaceAll("[\\\"\\\'][\\s]*javascript:(.*, arg1)[\\\"\\\']", "\"\"");
		value = value.replaceAll("(?i)script", "&#115;cript");
		value = value.replaceAll("(?i)iframe", "&#104;frame;");
		value = value.replaceAll("(?i)frameset", "frame-set");
		value = value.replaceAll("(?i)applet", "apple&#116;");
		value = value.replaceAll("(?i)javascript", "java-script");
		value = value.replaceAll("(?i)vbscript", "vb-script");
		value = value.replaceAll("(?i)onactivate", "on-activate");
		value = value.replaceAll("(?i)on-abort", "onabort");
		value = value.replaceAll("(?i)onafterprint", "on-afterprint");
		value = value.replaceAll("(?i)onafterupdate", "on-afterupdate");
		value = value.replaceAll("(?i)onbeforeactivate", "on-beforeactivate");
		value = value.replaceAll("(?i)onbeforecopy", "on-beforecopy");
		value = value.replaceAll("(?i)onbeforecut", "on-beforecut");
		value = value.replaceAll("(?i)onbeforedeactivate", "on-beforedeactivate");
		value = value.replaceAll("(?i)onbeforeeditfocus", "on-beforeeditfocus");
		value = value.replaceAll("(?i)onbeforepaste", "on-beforepaste");
		value = value.replaceAll("(?i)onbeforeunload", "on-beforeunload");
		value = value.replaceAll("(?i)onbeforeupdate", "on-beforeupdate");
		value = value.replaceAll("(?i)onblur", "on-blur");
		value = value.replaceAll("(?i)onbounce", "on-bounce");
		value = value.replaceAll("(?i)onbegin", "on-begin");
		value = value.replaceAll("(?i)oncanplay", "on-canplay");
		value = value.replaceAll("(?i)oncellchange", "on-cellchange");
		value = value.replaceAll("(?i)onchange", "on-change");
		value = value.replaceAll("(?i)onclick", "on-click");
		value = value.replaceAll("(?i)oncontextmenu", "on-contextmenu");
		value = value.replaceAll("(?i)oncontrolselect", "on-controlselect");
		value = value.replaceAll("(?i)oncopy", "on-copy");
		value = value.replaceAll("(?i)oncut", "on-cut");
		value = value.replaceAll("(?i)oncontentready", "on-contentready");
		value = value.replaceAll("(?i)oncontentsave", "on-contentsave");
		value = value.replaceAll("(?i)ondataavailable", "on-dataavailable");
		value = value.replaceAll("(?i)ondatasetchanged", "on-datasetchanged");
		value = value.replaceAll("(?i)ondatasetcomplete", "on-datasetcomplete");
		value = value.replaceAll("(?i)ondblclick", "on-dblclick");
		value = value.replaceAll("(?i)ondeactivate", "on-deactivate");
		value = value.replaceAll("(?i)ondetach", "on-detach");
		value = value.replaceAll("(?i)ondocumentready", "on-documentready");
		value = value.replaceAll("(?i)ondrag", "on-drag");
		value = value.replaceAll("(?i)ondragend", "on-dragend");
		value = value.replaceAll("(?i)ondragenter", "on-dragenter");
		value = value.replaceAll("(?i)ondragleave", "on-dragleave");
		value = value.replaceAll("(?i)ondragover", "on-dragover");
		value = value.replaceAll("(?i)ondragstart", "on-dragstart");
		value = value.replaceAll("(?i)ondragdrop", "on-dragdrop");
		value = value.replaceAll("(?i)ondrop", "on-drop");
		value = value.replaceAll("(?i)ondurationchange", "on-durationchange");
		value = value.replaceAll("(?i)onemptied", "on-emptied");
		value = value.replaceAll("(?i)onended", "on-ended");
		value = value.replaceAll("(?i)onerrorupdate", "on-errorupdate");
		value = value.replaceAll("(?i)onfilterchange", "on-filterchange");
		value = value.replaceAll("(?i)onfinish", "on-finish");
		value = value.replaceAll("(?i)onfocus", "on-focus");
		value = value.replaceAll("(?i)onfocusin", "on-focusin");
		value = value.replaceAll("(?i)onfocusout", "on-focusout");
		value = value.replaceAll("(?i)onhashchange", "on-hashchange");
		value = value.replaceAll("(?i)onhelp", "on-help");
		value = value.replaceAll("(?i)onhide", "on-hide");
		value = value.replaceAll("(?i)oninput", "on-input");
		value = value.replaceAll("(?i)onkeydown", "on-keydown");
		value = value.replaceAll("(?i)onkeypress", "on-keypress");
		value = value.replaceAll("(?i)onkeyup", "on-keyup");
		value = value.replaceAll("(?i)onlayoutcomplete", "on-layoutcomplete");
		value = value.replaceAll("(?i)onload", "on-load");
		value = value.replaceAll("(?i)onloadeddata", "on-loadeddata");
		value = value.replaceAll("(?i)onloadedmetadata", "on-loadedmetadata");
		value = value.replaceAll("(?i)onloadstart", "on-loadstart");
		value = value.replaceAll("(?i)onlosecapture", "on-losecapture");
		value = value.replaceAll("(?i)onmessage", "on-message");
		value = value.replaceAll("(?i)onmediacomplete", "on-mediacomplete");
		value = value.replaceAll("(?i)onmediaerror", "on-mediaerror");
		value = value.replaceAll("(?i)onmedialoadfailed", "on-medialoadfailed");
		value = value.replaceAll("(?i)onmousedown", "on-mousedown");
		value = value.replaceAll("(?i)onmouseenter", "on-mouseenter");
		value = value.replaceAll("(?i)onmouseleave", "on-mouseleave");
		value = value.replaceAll("(?i)onmousemove", "on-mousemove");
		value = value.replaceAll("(?i)onmouseout", "on-mouseout");
		value = value.replaceAll("(?i)onmouseover", "on-mouseover");
		value = value.replaceAll("(?i)onmouseup", "on-mouseup");
		value = value.replaceAll("(?i)onmousewheel", "on-mousewheel");
		value = value.replaceAll("(?i)onmove", "on-move");
		value = value.replaceAll("(?i)onmoveend", "on-moveend");
		value = value.replaceAll("(?i)onmovestart", "on-movestart");
		value = value.replaceAll("(?i)onoffline", "on-offline");
		value = value.replaceAll("(?i)ononline", "on-online");
		value = value.replaceAll("(?i)ononopenstatechange", "on-openstatechange");
		value = value.replaceAll("(?i)ononoutofsync", "on-outofsync");
		value = value.replaceAll("(?i)onpage", "on-page");
		value = value.replaceAll("(?i)onerror", "on-error");
		value = value.replaceAll("(?i)onpaste", "on-paste");
		value = value.replaceAll("(?i)onpause", "on-pause");
		value = value.replaceAll("(?i)onplay", "on-play");
		value = value.replaceAll("(?i)onplaying", "on-playing");
		value = value.replaceAll("(?i)onplaystatechange", "on-playstatechange");
		value = value.replaceAll("(?i)onprogress", "on-progress");
		value = value.replaceAll("(?i)onratechange", "on-ratechange");
		value = value.replaceAll("(?i)onpropertychange", "on-propertychange");
		value = value.replaceAll("(?i)onreadystatechange", "on-readystatechange");
		value = value.replaceAll("(?i)onrepeat", "on-repeat");
		value = value.replaceAll("(?i)onresume", "on-resume");
		value = value.replaceAll("(?i)onreset", "on-reset");
		value = value.replaceAll("(?i)onresize", "on-resize");
		value = value.replaceAll("(?i)onresizeend", "on-resizeend");
		value = value.replaceAll("(?i)onresizestart", "on-resizestart");
		value = value.replaceAll("(?i)onreverse", "on-reverse");
		value = value.replaceAll("(?i)onrowclick", "on-rowclick");
		value = value.replaceAll("(?i)onrowout", "on-rowout");
		value = value.replaceAll("(?i)onrowenter", "on-rowenter");
		value = value.replaceAll("(?i)onrowover", "on-rowover");
		value = value.replaceAll("(?i)onrowdelete", "on-rowdelete");
		value = value.replaceAll("(?i)onrowexit", "on-rowexit");
		value = value.replaceAll("(?i)onrowsdelete", "on-rowsdelete");
		value = value.replaceAll("(?i)onrowsinserted", "on-rowsinserted");
		value = value.replaceAll("(?i)onsave", "on-save");
		value = value.replaceAll("(?i)onseek", "on-seek");
		value = value.replaceAll("(?i)onscroll", "on-scroll");
		value = value.replaceAll("(?i)onseeked", "on-seeked");
		value = value.replaceAll("(?i)onseeking", "on-seeking");
		value = value.replaceAll("(?i)onselect", "on-select");
		value = value.replaceAll("(?i)onselectionchange", "on-selectionchange");
		value = value.replaceAll("(?i)onselectstart", "on-selectstart");
		value = value.replaceAll("(?i)onshow", "on-show");
		value = value.replaceAll("(?i)onstalled", "on-stalled");
		value = value.replaceAll("(?i)onstart", "on-start");
		value = value.replaceAll("(?i)onstop", "on-stop");
		value = value.replaceAll("(?i)onstorage", "on-storage");
		value = value.replaceAll("(?i)onstoragecommit", "on-storagecommit");
		value = value.replaceAll("(?i)onsubmit", "on-submit");
		value = value.replaceAll("(?i)onsuspend", "on-suspend");
		value = value.replaceAll("(?i)onsyncrestored", "on-syncrestored");
		value = value.replaceAll("(?i)ontimeerror", "on-timeerror");
		value = value.replaceAll("(?i)ontimeout", "on-timeout");
		value = value.replaceAll("(?i)ontimeupdate", "on-timeupdate");
		value = value.replaceAll("(?i)ontrackchange", "on-trackchange");
		value = value.replaceAll("(?i)onunload", "on-unload");
		value = value.replaceAll("(?i)onurlflip", "on-urlflip");
		value = value.replaceAll("(?i)onvolumechange", "on-volumechange");
		value = value.replaceAll("(?i)onwaiting", "on-waiting");

		return value;
	}
	
	public static double stringToDouble(String object) {
		double result = (object==null||object.isEmpty())?0:Double.parseDouble(object);
		return result;
	}
	
	public static double floatToDouble(float object) {
		double result = Double.parseDouble(Float.toString(object));
		return result;
	}
	
	public static double objectToDouble(Object object) {
		double result = 0;
		try {
			result = (object==null||((String)object).isEmpty())?0:Double.parseDouble((String)object);
		}catch (Exception e) {
			result = 0;
		}
		return result;
	}
	
	public static double decimalToDouble(Object object) {
		double result = 0;
		try {
			result = (object==null)?0:Double.parseDouble(String.valueOf(object));
		}catch (Exception e) {
			result = 0;
		}
		return result;
	}
	
	public static int decimalToInt(Object object) {
		int result = 0;
		try {
			result = (object==null)?0:Integer.parseInt(String.valueOf(object));
		}catch (Exception e) {
			result = 0;
		}
		return result;
	}
	
	
	public static int bigDecimalToInt(Object object) {
		int result = 0;
		try {
			result = (object==null)?0:Integer.parseInt(String.valueOf(object));
		}catch (Exception e) {
			try {
				result = (int) object;
			} catch (Exception e2) {
				result = ((BigDecimal)object).intValue();
			}
		}
		return result;
	}
	
	
	/**
	 * Object type 변수가 비어있는지 체크
	 * 
	 * @param obj
	 * @return Boolean : true / false
	 */
	public static Boolean empty(Object obj) {
		if (obj instanceof String)
			return obj == null || "".equals(obj.toString().trim());
		else if (obj instanceof List)
			return obj == null || ((List<?>) obj).isEmpty();
		else if (obj instanceof Map)
			return obj == null || ((Map<?, ?>) obj).isEmpty();
		else if (obj instanceof Object[])
			return obj == null || Array.getLength(obj) == 0;
		else
			return obj == null;
	}

	/**
	 * Object type 변수가 비어있지 않은지 체크
	 * 
	 * @param obj
	 * @return Boolean : true / false
	 */
	public static Boolean notEmpty(Object obj) {
		return !empty(obj);
	}

	public static double pointRound(double len,int cnt) {
		return Math.round(len*Math.pow(10, cnt+1))/Math.pow(10, cnt+1);
	}
	
	public static String objectToString(Object object) {
		String result = CommonUtil.empty(object)?"":(String)object;
		return result;
	}
	
	//ip가져오기.
	public static String getIpAddress(HttpServletRequest request) {
		String unkown = "unknown";
		String ipAddress = request.getHeader("X-Forwarded-For");
		if (ipAddress == null || ipAddress.length() == 0 || unkown.equalsIgnoreCase(ipAddress))
		{
			ipAddress = request.getHeader("Proxy-Client-ipAddress");
		}
		if (ipAddress == null || ipAddress.length() == 0 || unkown.equalsIgnoreCase(ipAddress))
		{
			ipAddress = request.getHeader("WL-Proxy-Client-ipAddress");
		}
		if (ipAddress == null || ipAddress.length() == 0 || unkown.equalsIgnoreCase(ipAddress))
		{
			ipAddress = request.getHeader("HTTP_CLIENT_ipAddress");
		}
		if (ipAddress == null || ipAddress.length() == 0 || unkown.equalsIgnoreCase(ipAddress))
		{
			ipAddress = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if (ipAddress == null || ipAddress.length() == 0 || unkown.equalsIgnoreCase(ipAddress))
		{
			ipAddress = request.getRemoteAddr();
		}
		return ipAddress;
	}
}
