package br.com.sankhya.chupacabrasnk.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.jdom2.Element;

public class SimpleXPath {
	List	tokens;
	boolean	singleNode;
	int		lastToken;
	Filter	filter;

	public static SimpleXPath build(String path) {
		return build(path, null);
	}

	public static SimpleXPath build(String path, Filter filter) {
		SimpleXPath xp = new SimpleXPath();
		xp.filter = filter;

		xp.tokens = new ArrayList();

		String[] v = path.split("/"); // path deve ser uma sentença XPath válida. Não suportamos critérios de busca baseados em atributos ainda.

		boolean nextIsAnyLevel = false;

		for (int i = 0; i < v.length; i++) {
			String token = v[i].trim();

			if (!token.equals(".")) {
				if (token.length() == 0) { // significa que é um '//' na XPath, e define uma Path relativa (qualquer posição anterior).
					nextIsAnyLevel = true;
				} else {
					XPathToken tk = new XPathToken();
					tk.anyLevel = nextIsAnyLevel;
					tk.name = token;
					nextIsAnyLevel = false;
					xp.tokens.add(tk);
				}
			}
		}

		xp.lastToken = xp.tokens.size() - 1;

		return xp;
	}

	public static String getNodeText(Element base, String xPath) {
		return getNodeText(base, xPath, null);
	}

	public static String getNodeText(Element base, String xPath, String defValue) {
		SimpleXPath xp = SimpleXPath.build(xPath);
		Element e = xp.selectSingleNode(base);

		return (e == null) ? defValue : e.getTextTrim();
	}

	public Element selectSingleNode(Element base) {
		singleNode = true;
		return (Element) search(base, 0);
	}

	public Collection<Element> selectNodes(Element base) {
		singleNode = false;
		return (Collection<Element>) search(base, 0);
	}

	public static Collection<Element> selectNodes(Element base, String xPath) {
		SimpleXPath xp = SimpleXPath.build(xPath);

		return xp.selectNodes(base);
	}

	public static Collection<Element> selectNodes(Element base, String xPath, Filter filter) {
		SimpleXPath xp = SimpleXPath.build(xPath, filter);

		return xp.selectNodes(base);
	}

	public static Element selectSingleNode(Element base, String xPath) {
		SimpleXPath xp = SimpleXPath.build(xPath);

		return xp.selectSingleNode(base);
	}

	public static Element selectSingleNode(Element base, String xPath, Filter filter) {
		SimpleXPath xp = SimpleXPath.build(xPath, filter);

		return xp.selectSingleNode(base);
	}

	private Object search(Element base, int currentToken) {
		XPathToken curToken = (XPathToken) tokens.get(currentToken);

		List children = base != null ? base.getChildren() : new ArrayList<Element>();

		List<Element> selectedNodes = null;

		if (!singleNode) {
			selectedNodes = new ArrayList<Element>();
		}

		//procuramos primeiro nos filhos diretos.
		for (Iterator ite = children.iterator(); ite.hasNext();) {
			Element child = (Element) ite.next();

			if ((curToken.name.equals("*") || curToken.name.equals(child.getName())) && (filter == null || filter.accept(child))) {
				int nextToken = currentToken + 1;

				if (nextToken > lastToken) { //achou !
					if (singleNode) {
						return child;
					}

					selectedNodes.add(child);
				} else {
					Object result = search(child, nextToken);

					if (result != null) { // achou !
						if (singleNode) {
							return result;
						}

						selectedNodes.addAll((Collection<Element>) result);
					}
				}
			}
		}

		// Não achamos nos filhos diretos. Se possível vamos procurar hierarquia abaixo.
		if (curToken.anyLevel) {
			for (Iterator ite = children.iterator(); ite.hasNext();) {
				Element child = (Element) ite.next();

				Object result = search(child, currentToken);

				if (result != null) { // achou !
					if (singleNode) {
						return result;
					}

					selectedNodes.addAll((Collection<Element>) result);
				}
			}
		}

		if (singleNode) {
			return null;// insucesso!!
		}

		return selectedNodes;
	}

	public static interface Filter {
		boolean accept(Element elem);
	}

	private static class XPathToken {
		String	name;
		boolean	anyLevel;
	}
}
