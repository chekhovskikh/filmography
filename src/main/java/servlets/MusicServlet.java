package servlets;

import com.google.inject.Inject;
import com.google.inject.Injector;
import music.Controller;
import music.Model;
import music.guice.InjectorBuilder;
import servlets.util.WebUtils;

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

public abstract class MusicServlet extends HttpServlet {

    public static final String PATH_PROPERTIES_ORACLE = "/oracle.properties";

    protected Model model = null;
    protected Controller controller = null;

    @Inject
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        try {
            InputStream inputProperties = getClass().getResourceAsStream(PATH_PROPERTIES_ORACLE);
            Properties properties = new Properties();
            properties.load(inputProperties);

            Injector injector = new InjectorBuilder(properties).getInjector();
            controller = injector.getInstance(Controller.class);
            model = controller.getModel();

        } catch (IOException e) {
            e.printStackTrace();
        }
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
