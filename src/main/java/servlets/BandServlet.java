package servlets;

import entities.Band;
import entities.filters.BandFilter;
import servlets.util.WebUtils;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

public class BandServlet extends MusicServlet {

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        if (WebUtils.isAjax(request)) {
            try {
                int id = Integer.parseInt(request.getParameter("id"));
                WebUtils.sendData(response, model.getBand(id));
            } catch (Exception e) {
                WebUtils.sendBadRequest(response, e.getCause().getMessage());
            }
            return;
        }

        List<Band> bands = null;
        Band selectedBand = null;
        try {
            bands = model.getBands();
            if (request.getParameter("id") != null) {
                int id = Integer.parseInt(request.getParameter("id"));
                selectedBand = model.getBand(id);
            }
        } catch (SQLException | ParseException | NumberFormatException e) {
            error(request, response,  "/bands", e.getMessage());
        }
        request.setAttribute("selectedBand", selectedBand);
        request.setAttribute("bands", bands);
        request.getRequestDispatcher("bands.jsp").forward(request, response);
    }

    protected void doAdd(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException, ParseException {
        Band band = WebUtils.getData(request, Band.class);
        controller.addBand(band);
    }

    protected void doRemove(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
        int id = WebUtils.getData(request, Integer.class);
        controller.removeBand(id);
    }

    protected void doUpdate(HttpServletRequest request, HttpServletResponse response) throws IOException, ParseException, SQLException {
        Band band = WebUtils.getData(request, Band.class);
        controller.updateBand(band);
    }

    protected void doSearch(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException, ParseException {
        String name = WebUtils.getData(request, String.class);
        WebUtils.sendData(response, model.getBandByName(name));
    }

    protected void doExtendedSearch(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException, ParseException {
        BandFilter bandFilter = WebUtils.getData(request, BandFilter.class);
        WebUtils.sendData(response, model.getBandByFilter(bandFilter));
    }
}
