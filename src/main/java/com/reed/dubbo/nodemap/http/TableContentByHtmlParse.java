package com.reed.dubbo.nodemap.http;

/**
 * html 解析dubbo monitor 页面
 */
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.HTMLElementName;
import net.htmlparser.jericho.Source;

public class TableContentByHtmlParse {

	public static Element getTableContent(Element element, int rows, int cols) {
		Element resultElement = null;
		List<Element> trList = element.getAllElements(HTMLElementName.TR);
		if (rows < trList.size()) {
			Element trElement = trList.get(rows);
			List<Element> tdList = trElement.getAllElements(HTMLElementName.TD);
			if (cols < tdList.size()) {
				// Element tdElement=tdList.get(cols);
				resultElement = tdList.get(cols);
			}
		}
		return resultElement;
	}

	/**
	 * get all applications name form dubbo
	 * @param url
	 * @return
	 */
	public static Set<String> getApplications(String url) {
		Set<String> r = new HashSet<String>();
		Source source = null;
		if (url != null) {
			url = url.contains("http://") ? url : "http://" + url;
			try {
				source = new Source(new URL(url));
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			List<Element> elementList = source
					.getAllElements(HTMLElementName.TABLE);
			if (elementList != null && elementList.size() > 1) {
				Element table = elementList.get(1);
				if (table != null && !table.isEmpty()) {
					List<Element> trs = table
							.getAllElements(HTMLElementName.TR);
					if (trs != null && trs.size() > 0) {
						for (int i = 2; i < trs.size(); i++) {
							Element currentElement = getTableContent(table, i,
									0);
							if (currentElement != null)
								r.add(currentElement.getContent().toString());
						}
					}
				}
			}
		}
		return r;
	}

	public static String getDependens(String url) {
		StringBuffer sf = new StringBuffer("<ul id=\"org\">");
		Source source = null;
		if (url != null) {
			url = url.contains("http://") ? url : "http://" + url;
			try {
				source = new Source(new URL(url));
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			List<Element> elementList = source
					.getAllElements(HTMLElementName.TABLE);
			if (elementList != null && elementList.size() > 1) {
				Element table = elementList.get(1);
				if (table != null && !table.isEmpty()) {
					List<Element> trs = table
							.getAllElements(HTMLElementName.TR);
					List<String> tds = new ArrayList<String>();
					if (trs != null && trs.size() > 0) {
						for (int i = 2; i < trs.size(); i++) {
							Element currentElement = getTableContent(table, i,
									0);
							if (currentElement != null) {
								String td = currentElement.getContent()
										.toString();
								if (td != null) {
									String[] strs = td.split("\\|");
									if (strs.length < 6) {
										tds.add(td);
									}
								}
							}
						}
						buildTree(sf, tds);
					}
				}
			}
		}
		sf.append("</ul>");
		return sf.toString();
	}

	public static void buildTree(StringBuffer sf, List<String> tds) {
		if (tds != null && tds.size() > 0) {
			int ulCount = 0;
			int liCount = 0;
			int lastLevel = 0;
			for (String td : tds) {
				String[] strs = td.split("\\|");
				int size = strs.length - 1;
				// root
				if (size == 0) {
					sf.append("<li>" + td);
					liCount++;
				} else {
					td = strs[size].replace("-->", "").replace("--&gt; ", "")
							.trim();
					if (size == lastLevel) {
						sf.append("</li>");
						liCount--;
					}
					if (size > lastLevel) {
						// child
						sf.append("<ul>");
						ulCount++;
					}
					if (size < lastLevel) {
						int i = lastLevel - size;
						while (i > 0) {
							sf.append("</li></ul>");
							liCount--;
							ulCount--;
							i--;
						}
						sf.append("</li>");
						liCount--;
					}
					sf.append("<li>" + td);
					liCount++;

				}
				lastLevel = size;
			}
			for (int i = liCount; i > 0; i--) {
				sf.append("</li>");
				for (int j = ulCount; j > 0; j--) {
					sf.append("</ul>");
				}
			}
			sf.append("</li>");
		}
	}

	public static void main(String[] args) {
		// getApplications("http://172.28.18.196:6060/applications.html");
		System.out
				.println(getDependens("http://172.28.18.196:6060/dependencies.html?application=UserQueueHandle"));
	}
}