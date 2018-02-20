package servlet;

import entitiy.Film;
import entitiy.filter.FilmFilter;
import servlet.util.WebUtil;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

public class FilmServlet extends BaseServlet {

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        if (WebUtil.isAjax(request)) {
            try {
                int id = Integer.parseInt(request.getParameter("id"));
                WebUtil.sendData(response, model.getFilm(id));
            } catch (Exception e) {
                WebUtil.sendBadRequest(response, e.getCause().getMessage());
            }
            return;
        }

        List<Film> films = null;
        Film selectedFilm = null;
        String genreName = null;
        String producerName = null;
        String franchiseName = null;
        try {
            films = model.getFilms();
            if (request.getParameter("id") != null) {
                int id = Integer.parseInt(request.getParameter("id"));
                selectedFilm = model.getFilm(id);

                if (selectedFilm.getGenreId() != 0)
                    genreName = model.getGenre(selectedFilm.getGenreId()).getGenreName();
                if (selectedFilm.getProducerId() != 0)
                    producerName = model.getProducer(selectedFilm.getProducerId()).getProducerName();
                if (selectedFilm.getFranchiseId() != 0)
                    franchiseName = model.getFranchise(selectedFilm.getFranchiseId()).getFranchiseName();
            }
        } catch (SQLException | ParseException | NumberFormatException e) {
            error(request, response,  "/films", e.getMessage());
        }
        request.setAttribute("selectedFilm", selectedFilm);
        request.setAttribute("genreName", genreName);
        request.setAttribute("producerName", producerName);
        request.setAttribute("franchiseName", franchiseName);
        request.setAttribute("films", films);
        request.getRequestDispatcher("films.jsp").forward(request, response);
    }

    protected void doAdd(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException, ParseException {
        Film film = WebUtil.getData(request, Film.class);
        controller.addFilm(film);
    }

    protected void doRemove(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
        int id = WebUtil.getData(request, Integer.class);
        controller.removeFilm(id);
    }

    protected void doUpdate(HttpServletRequest request, HttpServletResponse response) throws IOException, ParseException, SQLException {
        Film film = WebUtil.getData(request, Film.class);
        controller.updateFilm(film);
    }

    protected void doSearch(HttpServletRequest request, HttpServletResponse response) throws IOException, ParseException, SQLException {
        String name = WebUtil.getData(request, String.class);
        WebUtil.sendData(response, model.getFilmByName(name));
    }

    protected void doExtendedSearch(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException, ParseException {
        FilmFilter filmFilter = WebUtil.getData(request, FilmFilter.class);
        WebUtil.sendData(response, model.getFilmByFilter(filmFilter));
    }
}

