package servlet;

import com.google.inject.Inject;
import com.google.inject.Injector;
import mvc.Controller;
import mvc.Model;
import servlet.util.DataLayer;
import util.binder.InjectorBuilder;
import servlet.util.WebUtils;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Properties;

public abstract class BaseServlet extends HttpServlet {

    public static final String PATH_PROPERTIES_POSTGRES = "/postgres.properties";

    protected Model model = null;
    protected Controller controller = null;

    @Inject
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        controller = DataLayer.getInstance().getController();
        model = DataLayer.getInstance().getModel();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        if (!WebUtils.isAjax(request))
            return;

        String action = request.getParameter("action");
        try {
            switch (action.toLowerCase()) {
                case "put":
                    doAdd(request, response);
                    break;
                case "delete":
                    doRemove(request, response);
                    break;
                case "update":
                    doUpdate(request, response);
                    break;
                case "search":
                    doSearch(request, response);
                    break;
                case "extendedsearch":
                    doExtendedSearch(request, response);
                    break;
            }
        } catch (Exception e) {
            WebUtils.sendBadRequest(response, e.getCause().getMessage());
        }
    }

    protected void error(HttpServletRequest request, HttpServletResponse response, String prevPage, String msg) throws ServletException, IOException {
        request.setAttribute("errorMsg", msg);
        request.setAttribute("prevPage", prevPage);
        request.getRequestDispatcher("error.jsp").forward(request, response);
    }

    protected abstract void doAdd(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException, ParseException;

    protected abstract void doRemove(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException, ParseException;

    protected abstract void doUpdate(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException, ParseException;

    protected abstract void doSearch(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException, ParseException;

    protected abstract void doExtendedSearch(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException, ParseException;
}
