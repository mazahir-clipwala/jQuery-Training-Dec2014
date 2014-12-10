package com.test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Servlet implementation class Orders
 */
@WebServlet({ "/orders", "/orders/json" })
public class Orders extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private final static String ORDERS = "orders"; 
	private final static String ORDER_NUM = "ordernum";
	private final static String NAME = "name";
	private final static String ITEM = "item";
	
	LinkedHashMap<String, ArrayList<String>> ordersList = new LinkedHashMap<String, ArrayList<String>> ();
	
	public Orders() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (0 == ordersList.size ()) {
			response.setStatus (404);
			response.getWriter ().write ("There are no orders in the system");
			response.getWriter ().close ();
			return;
		}
		
		if (("/OrderMgmt/orders/json").equalsIgnoreCase (request.getRequestURI ())) {
			response.setContentType("application/JSON");
			
			Iterator <ArrayList<String>> iter = ordersList.values().iterator();
			
			JSONObject jsonWrap = new JSONObject ();
			JSONArray jsonArr = new JSONArray ();
			while (iter.hasNext ()) {
				ArrayList<String> order = iter.next();
				
				JSONObject jsonObj = new JSONObject ();
				
				jsonObj.put(ORDER_NUM, order.get (0));
				jsonObj.put(NAME, order.get (1));
				jsonObj.put(ITEM,order.get (2));
				
				jsonArr.put(jsonObj);
			}
			
			jsonWrap.put(ORDERS, jsonArr);
			
			response.getWriter ().println (jsonWrap.toString ());
			response.getWriter ().close ();
		}
		else {
			OrdersXML xml = new OrdersXML ();
			
			xml.writeXML (ordersList);
			String xmlString = xml.readXML();
			
			response.getWriter ().write (xmlString);
			response.getWriter ().close ();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String name = request.getParameter ("name");
		String item = request.getParameter ("item");
		
		ArrayList <String> order = new ArrayList<String>();
		order.add (0, String.valueOf (ordersList.size ()));
		order.add (1, name);
		order.add (2, item);
		
		ordersList.put (name, order);
		
		response.getWriter().write ("success in your post");
		response.getWriter().close ();
		
		
	}
}
