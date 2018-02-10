package servlet;

import entitiy.Franchise;
import entitiy.filter.FranchiseFilter;
import servlet.util.WebUtils;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

public class FranchiseServlet extends BaseServlet {

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        if (WebUtils.isAjax(request)) {
            try {
                int id = Integer.parseInt(request.getParameter("id"));
                WebUtils.sendData(response, model.getFranchise(id));
            } catch (Exception e) {
                WebUtils.sendBadRequest(response, e.getCause().getMessage());
            }
            return;
        }

        List<Franchise> franchises = null;
        Franchise selectedFranchise = null;
        try {
            franchises = model.getFranchises();
            if (request.getParameter("id") != null) {
                int id = Integer.parseInt(request.getParameter("id"));
                selectedFranchise = model.getFranchise(id);
            }
        } catch (SQLException | ParseException | NumberFormatException e) {
            error(request, response, "/franchises", e.getMessage());
        }
        request.setAttribute("selectedFranchise", selectedFranchise);
        request.setAttribute("franchises", franchises);
        request.getRequestDispatcher("franchises.jsp").forward(request, response);
    }

    protected void doAdd(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException, ParseException {
        Franchise franchise = WebUtils.getData(request, Franchise.class);
        controller.addFranchise(franchise);
    }

    protected void doRemove(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
        int id = WebUtils.getData(request, Integer.class);
        controller.removeFranchise(id);
    }

    protected void doUpdate(HttpServletRequest request, HttpServletResponse response) throws IOException, ParseException, SQLException {
        Franchise franchise = WebUtils.getData(request, Franchise.class);
        controller.updateFranchise(franchise);
    }

    protected void doSearch(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException, ParseException {
        String name = WebUtils.getData(request, String.class);
        WebUtils.sendData(response, model.getFranchiseByName(name));
    }

    protected void doExtendedSearch(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException, ParseException {
        FranchiseFilter franchiseFilter = WebUtils.getData(request, FranchiseFilter.class);
        WebUtils.sendData(response, model.getFranchiseByFilter(franchiseFilter));
    }
}
