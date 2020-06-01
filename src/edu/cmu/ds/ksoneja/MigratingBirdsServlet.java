package edu.cmu.ds.ksoneja;

import javax.servlet.RequestDispatcher;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "MigratingBirdsServlet",
        urlPatterns = {"/", "/index","/next","/res","/birds"})
public class MigratingBirdsServlet extends javax.servlet.http.HttpServlet {

    BirdsModel bm = null;
    @Override
    public void init() {
        bm = new BirdsModel();
    }
    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {

    }

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {

            String search = request.getParameter("inputstring");
            String ua = request.getHeader("User-Agent");

            boolean mobile;
            if (ua != null && ((ua.indexOf("Android") != -1) || (ua.indexOf("iPhone") != -1))) {
                mobile = true;
                /*
                 * This is the latest XHTML Mobile doctype. To see the difference it
                 * makes, comment it out so that a default desktop doctype is used
                 * and view on an Android or iPhone.
                 */
                request.setAttribute("doctype", "<!DOCTYPE html PUBLIC \"-//WAPFORUM//DTD XHTML Mobile 1.2//EN\" \"http://www.openmobilealliance.org/tech/DTD/xhtml-mobile12.dtd\">");
            } else {
                mobile = false;
                request.setAttribute("doctype", "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">");
            }

            String nextView = null;
            //Reference to the first index page
            if(request.getServletPath().equalsIgnoreCase("/index")){
                String picSize = (mobile) ? "mobile" : "desktop";
                List<String> birdURL = bm.doBirdSearch(search,picSize);
                request.setAttribute("pictureURL",birdURL);
                nextView = "nextpage.jsp";
            }
            //Reference to the second page
            else if ( request.getServletPath().equalsIgnoreCase("/next")){
                String temp = request.getParameter("pc");
                String xyz = bm.imageURL(temp);
                nextView = "result.jsp";
                String ntemp5[] = xyz.split("}");
                request.setAttribute("abc",ntemp5[0]);
                request.setAttribute("credit",ntemp5[1]);
                request.setAttribute("link","https://nationalzoo.si.edu/scbi/migratorybirds/featured_photo/");
                request.setAttribute("birdchosen",temp);
            }
            else if(request.getServletPath().equalsIgnoreCase("/birds") || request.getServletPath().equalsIgnoreCase("/")) {
                nextView = "index.jsp";
            }


            //Setting final view
            RequestDispatcher view = request.getRequestDispatcher(nextView);
            view.forward(request, response);
    }
}
