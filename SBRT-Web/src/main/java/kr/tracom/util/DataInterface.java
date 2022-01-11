package kr.tracom.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import kr.tracom.util.domain.LocationVO;

public class DataInterface {

	
	public static String interface_URL(String requestType, String url) {
		try {
			HttpResponse<String> response = null;
			if(requestType.equals("GET")) {
				response = Unirest.get(url).asString();
			}
			else if(requestType.equals("POST")) {
				response = Unirest.post(url).asString();				
			}
			
			return response.getBody();
			
		} catch (UnirestException e) {
			e.printStackTrace();
			return "false";
		}
	}
	
	public static List<Map<String, Object>> parseJsonRouteNode(String jsonString) throws ParseException {
		JSONParser Parser = new JSONParser();
		JSONObject jsonObj = (JSONObject) Parser.parse(jsonString);
		JSONArray busRouteDetailMapVoList = (JSONArray) jsonObj.get("busRouteDetailMapVoList");
		
		int i=1;
		int tmp = 0;
		Double tmpLati = (double) 0;
		Double tmpLongi = (double) 0;
		
		List<Map<String, Object>> resultList = new ArrayList<>();

		for(int j = 0; j < busRouteDetailMapVoList.size(); j++) {//
			Map<String, Object> vo = new HashMap();
			Object o = busRouteDetailMapVoList.get(j);//
			JSONObject ob = (JSONObject)o;
			
			String route_ord = ob.get("route_ord").toString().replace("\"", "");
			String ord = ob.get("ord").toString().replace("\"", "");
			String route_id = ob.get("route_id").toString().replace("\"", "");
			Double lati = Double.valueOf(ob.get("lat").toString().replace("\"", ""));
			Double longi = Double.valueOf(ob.get("lng").toString().replace("\"", ""));
			int nodeType = 30;
			if(j == 0) {
				tmp = Integer.valueOf(route_ord);
			}
			
			if(tmp != Integer.valueOf(route_ord)){
				tmp = Integer.valueOf(route_ord);
				continue;
			}
			if(tmpLati == lati && tmpLongi == longi) {
				
			}
			else {				
				vo.put("NODE_NM",route_id + "_" + route_ord + "_" + ord);
				vo.put("GPS_Y", lati);
				vo.put("GPS_X", longi);
				vo.put("NODE_TYPE", Constants.NODE_TYPE_NORMAL);
				resultList.add(vo);
				i++;
			}
			
			tmpLati = lati;
			tmpLongi = longi;
		}
		//return resultList;
		return generalNode(resultList);
	}
	
	public static NodeList interface_XML(String inputUrl) {
		BufferedReader br = null;
		//DocumentBuilderFactory 생성
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setNamespaceAware(true);
		DocumentBuilder builder;
		Document doc = null;
		try {
			// OpenApi호출

			URL url = new URL(inputUrl);
			HttpURLConnection urlconnection = (HttpURLConnection) url.openConnection();

			// 응답 읽기
			br = new BufferedReader(new InputStreamReader(urlconnection.getInputStream(), "UTF-8"));
			String result = "";
			String line;
			while ((line = br.readLine()) != null) {
				result = result + line.trim();// result = URL로 XML을 읽은 값
			}

			// xml 파싱하기
			InputSource is = new InputSource(new StringReader(result));
			builder = factory.newDocumentBuilder();
			doc = builder.parse(is);
			XPathFactory xpathFactory = XPathFactory.newInstance();
			XPath xpath = xpathFactory.newXPath();

			XPathExpression expr = xpath.compile("//items/item");
			NodeList nodeList = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);

			return nodeList;

		} catch (Exception e) {
			return null;
		}
	}

	/**한점이 직선상에 직교좌표를 생성한 좌표를 반환**/
	public static LocationVO getPointToLine(double x, double y, double x1, double y1, double x2, double y2) {
		boolean isValid = false;
		LocationVO point = new LocationVO();
		
		if(y1 == y2 && x1 == y2)
			y1 -= 0.00001;
		double U = ((y - y1) * (y2 - y1)) + ((x - x1) * (x2 - x1));
		double Udenom = Math.pow(y2 - y1, 2) + Math.pow(x2 - x1, 2);
		
		U /= Udenom;
		
		y = y1 + (U * (y2 - y1));
		x = x1 + (U * (x2 - x1));
		point.setX(x);
		point.setY(y);
		
		double minx, maxx, miny, maxy;
		
		minx = Math.min(y1, y2);
		maxx = Math.max(y1, y2);
		
		miny = Math.min(x1, x2);
		maxy = Math.max(x1, x2);
		
		isValid = (point.getY() >= minx && point.getY() <= maxx) && (point.getX() >= miny && point.getX() <= maxy);
		return isValid ? point : null;
	}
	
	/**한점이 직선에 직교좌표를 생성하고 거리를 계산**/
	public static LocationVO getDistanceToLine(double x, double y, double x1, double y1, double x2, double y2) {
		LocationVO point = getPointToLine(x, y, x1, y1, x2, y2);
		
		if(point == null) {
			return null;
		} else {
			double distance = getDistanceBetween(x, y, point.getX(), point.getY());
			if(distance < 999999999) {
				point.setDistance(distance);
			}
			return point;
		}
	}
	
	public static List insertNodeToNode(List<Map<String, Object>> nodeList, List<Map<String, Object>> sourceList) {
		// 정류장 갯수만큼 for문 돌릴거임
		for (Map<String, Object> sta : sourceList) {
			int forseq = 0;
			LocationVO resultVO = new LocationVO();
			LocationVO tmpVO = new LocationVO();
			resultVO.setDistance(999999999);

			int shortestNodeSeq = 0;
			double shortestNodeDst = 999999999;
			double staGpsX = CommonUtil.objectToDouble(sta.get("GPS_X"));
			double staGpsY = CommonUtil.objectToDouble(sta.get("GPS_Y"));
			for (int i = 0; i < nodeList.size(); i++) {
				// 노드마다 거리 계산할것임
				double nodeGpsX = CommonUtil.objectToDouble( nodeList.get(i).get("GPS_X"));
				double nodeGpsY = CommonUtil.objectToDouble( nodeList.get(i).get("GPS_Y"));
				
				double tmp = getDistanceBetween(staGpsX, staGpsY, nodeGpsX, nodeGpsY);
				// 가장 가까운 노드 순서
				if (tmp < shortestNodeDst) {
					shortestNodeDst = tmp;
					shortestNodeSeq = i;
				}

			}

			for (int j = -2; j < 2; j++) {
				if (shortestNodeSeq + j < 0) {
					continue;
				} else if (0 <= shortestNodeSeq + j && shortestNodeSeq + j + 1 < nodeList.size()) {
					double temp1_gpsX = CommonUtil.objectToDouble(nodeList.get(shortestNodeSeq + j).get("GPS_X"));
					double temp1_gpsY = CommonUtil.objectToDouble(nodeList.get(shortestNodeSeq + j).get("GPS_Y"));
					double temp2_gpsX = CommonUtil.objectToDouble(nodeList.get(shortestNodeSeq + j+1).get("GPS_X"));
					double temp2_gpsY = CommonUtil.objectToDouble(nodeList.get(shortestNodeSeq + j+1).get("GPS_Y"));
					tmpVO = getDistanceToLine(staGpsX, staGpsY, temp1_gpsX, temp1_gpsY, temp2_gpsX, temp2_gpsY);

					// 만약 직교한다면
					if (tmpVO != null) {
						// 가장 짧은값이라면
						if (tmpVO.getDistance() < resultVO.getDistance()) {
							// 바꿔치기함
							resultVO = tmpVO;
							// forseq는 리스트에 삽입할 순서
							forseq = shortestNodeSeq + j + 1;
						}
					}
				}
			}
			nodeList.add(forseq, sta);

		} // 정류장for end

		return nodeList;
	}

	public static List<Map<String, Object>> generalNode(List<Map<String, Object>> nodeList){
		double tmp = 0;
		List<Map<String, Object>> voList = new ArrayList<>();
		List<Map<String, Object>> resultList = new ArrayList<>();
		
		for(int i=0; i<nodeList.size()-1; i++) {
			if(i == 0) {
				voList.add(nodeList.get(i));
			}
			
			if(i == nodeList.size() - 2) {
				voList.add(nodeList.get(nodeList.size()-1));
				break;
			}
			
			double lati1		= (double) nodeList.get(i).get("GPS_Y");
			double longi1	= (double) nodeList.get(i).get("GPS_X");
			double lati2		= (double) nodeList.get(i+1).get("GPS_Y");
			double longi2	= (double) nodeList.get(i+1).get("GPS_X");
			
			double dist = getDistanceBetween(longi1, lati1, longi2, lati2);
			
			//거리가 60이하인경우
			if(tmp + dist <= 60) {
				tmp += dist;
			}
			else if(tmp + dist > 60) {
				tmp = 0;
				voList.add(nodeList.get(i+1));
			}
		}
		return voList;
	}
	
	/*소스 list와 거리가 60이하인 경우 삭제*/
	public static List<Map<String, Object>> generalNode2(List<Map<String, Object>> sourceList, List<Map<String, Object>> nodeList){
		List<Map<String, Object>> voList = new ArrayList<>();
		List<Map<String, Object>> resultList = new ArrayList<>();
		
		for(int i=0; i<nodeList.size(); i++) {
			
			if(i == 0) {
				voList.add(nodeList.get(i));
				continue;
			}
			
			if(i == nodeList.size() - 1) {
				voList.add(nodeList.get(nodeList.size()-1));
				break;
			}
			
			String nodeType = (String)nodeList.get(i).get("NODE_TYPE");
			if(nodeType.equals(Constants.NODE_TYPE_CROSS)||nodeType.equals(Constants.NODE_TYPE_BUSSTOP)) {
				voList.add(nodeList.get(i));
			}
			else {
				double dist = 0;
				for(int j=0; j<sourceList.size(); j++) {
					double lati1	= CommonUtil.objectToDouble(sourceList.get(j).get("GPS_Y"));
					double longi1	= CommonUtil.objectToDouble(sourceList.get(j).get("GPS_X"));
					double lati2	= CommonUtil.objectToDouble(nodeList.get(i).get("GPS_Y"));
					double longi2	= CommonUtil.objectToDouble(nodeList.get(i).get("GPS_X"));
					
					dist = getDistanceBetween(longi1, lati1, longi2, lati2);
					
					//거리가 60이하인경우
					if(dist <= 60) {
						break;
					}
				}
				if(dist > 60) {
					voList.add(nodeList.get(i));
				}
			}
		}

		return voList;
	}
	
	/**두 지점간 거리 계산**/
	public static double getDistanceBetween(double x1, double y1, double x2, double y2) {
		double kEarthRadiusKms = 6376.5;
		
		double lat1_rad = y1 * (Math.PI/180);
		double lng1_rad = x1 * (Math.PI/180);
		double lat2_rad = y2 * (Math.PI/180);
		double lng2_rad = x2 * (Math.PI/180);
		
		double lat_gap = lat2_rad - lat1_rad;
		double lng_gap = lng2_rad - lng1_rad;
		
		double mid_val = Math.pow(Math.sin(lat_gap / 2.0), 2.0)
				+ Math.cos(lat1_rad) * Math.cos(lat2_rad) * Math.pow(Math.sin(lng_gap / 2.0), 2.0);

		double circle_distance = 2.0 * Math.atan2(Math.sqrt(mid_val), Math.sqrt(1.0 - mid_val));
		double distance = kEarthRadiusKms * circle_distance * 1000;

		return distance;
	}
	
	public static String getTagValue(String tag, Element eElement) {
		NodeList nList = eElement.getElementsByTagName(tag).item(0).getChildNodes();
		
		Node node = (Node)nList.item(0);
		if(node == null) {
			return null;
		}
		return node.getNodeValue();
	}
	
}
