package servlet;

import entitiy.Genre;
import entitiy.filter.GenreFilter;
import servlet.util.WebUtils;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

public class GenreServlet extends BaseServlet {

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        if (WebUtils.isAjax(request)) {
            try {
                int id = Integer.parseInt(request.getParameter("id"));
                WebUtils.sendData(response, model.getGenre(id));
            } catch (Exception e) {
                WebUtils.sendBadRequest(response, e.getCause().getMessage());
            }
            return;
        }

        List<Genre> genres = null;
        Genre selectedGenre = null;
        String parentName = null;
        try {
            genres = model.getGenres();
            if (request.getParameter("id") != null) {
                int id = Integer.parseInt(request.getParameter("id"));
                selectedGenre = model.getGenre(id);

                if (selectedGenre.getParentId() != 0)
                    parentName = model.getGenre(selectedGenre.getParentId()).getGenreName();
            }
        } catch (SQLException | ParseException | NumberFormatException e) {
            error(request, response,  "/genres", e.getMessage());
        }
        request.setAttribute("selectedGenre", selectedGenre);
        request.setAttribute("parentName", parentName);
        request.setAttribute("genres", genres);
        request.getRequestDispatcher("genres.jsp").forward(request, response);
    }

    protected void doAdd(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException, ParseException {
        Genre genre = WebUtils.getData(request, Genre.class);
        controller.addGenre(genre);
    }

    protected void doRemove(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
        int id = WebUtils.getData(request, Integer.class);
        controller.removeGenre(id);
    }

    protected void doUpdate(HttpServletRequest request, HttpServletResponse response) throws IOException, ParseException, SQLException {
        Genre genre = WebUtils.getData(request, Genre.class);
        controller.updateGenre(genre);
    }

    protected void doSearch(HttpServletRequest request, HttpServletResponse response) throws IOException, ParseException, SQLException {
        String name = WebUtils.getData(request, String.class);
        WebUtils.sendData(response, model.getGenreByName(name));
    }

    protected void doExtendedSearch(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException, ParseException {
        GenreFilter genreFilter = WebUtils.getData(request, GenreFilter.class);
        WebUtils.sendData(response, model.getGenreByFilter(genreFilter));
    }
}