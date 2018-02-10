package servlet;

import entitiy.Producer;
import entitiy.filter.ProducerFilter;
import servlet.util.WebUtils;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

public class ProducerServlet extends BaseServlet {

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        if (WebUtils.isAjax(request)) {
            try {
                int id = Integer.parseInt(request.getParameter("id"));
                WebUtils.sendData(response, model.getProducer(id));
            } catch (Exception e) {
                WebUtils.sendBadRequest(response, e.getCause().getMessage());
            }
            return;
        }

        List<Producer> producers = null;
        Producer selectedProducer = null;
        try {
            producers = model.getProducers();
            if (request.getParameter("id") != null) {
                int id = Integer.parseInt(request.getParameter("id"));
                selectedProducer = model.getProducer(id);
            }
        } catch (SQLException | ParseException | NumberFormatException e) {
            error(request, response,  "/producers", e.getMessage());
        }
        request.setAttribute("selectedProducer", selectedProducer);
        request.setAttribute("producers", producers);
        request.getRequestDispatcher("producers.jsp").forward(request, response);
    }

    protected void doAdd(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException, ParseException {
        Producer producer = WebUtils.getData(request, Producer.class);
        controller.addProducer(producer);
    }

    protected void doRemove(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
        int id = WebUtils.getData(request, Integer.class);
        controller.removeProducer(id);
    }

    protected void doUpdate(HttpServletRequest request, HttpServletResponse response) throws IOException, ParseException, SQLException {
        Producer producer = WebUtils.getData(request, Producer.class);
        controller.updateProducer(producer);
    }

    protected void doSearch(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException, ParseException {
        String name = WebUtils.getData(request, String.class);
        WebUtils.sendData(response, model.getProducerByName(name));
    }

    protected void doExtendedSearch(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException, ParseException {
        ProducerFilter producerFilter = WebUtils.getData(request, ProducerFilter.class);
        WebUtils.sendData(response, model.getProducerByFilter(producerFilter));
    }
}
