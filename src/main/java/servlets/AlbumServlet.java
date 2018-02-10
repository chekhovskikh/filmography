package servlets;

import entities.Album;
import entities.filters.AlbumFilter;
import servlets.util.WebUtils;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

public class AlbumServlet extends MusicServlet {

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        if (WebUtils.isAjax(request)) {
            try {
                int id = Integer.parseInt(request.getParameter("id"));
                WebUtils.sendData(response, model.getAlbum(id));
            } catch (Exception e) {
                WebUtils.sendBadRequest(response, e.getCause().getMessage());
            }
            return;
        }

        List<Album> albums = null;
        Album selectedAlbum = null;
        String bandName = null;
        try {
            albums = model.getAlbums();
            if (request.getParameter("id") != null) {
                int id = Integer.parseInt(request.getParameter("id"));
                selectedAlbum = model.getAlbum(id);

                if (selectedAlbum.getBandId() != 0)
                    bandName = model.getBand(selectedAlbum.getBandId()).getName();
            }
        } catch (SQLException | ParseException | NumberFormatException e) {
            error(request, response, "/albums", e.getMessage());
        }
        request.setAttribute("selectedAlbum", selectedAlbum);
        request.setAttribute("bandName", bandName);
        request.setAttribute("albums", albums);
        request.getRequestDispatcher("albums.jsp").forward(request, response);
    }

    protected void doAdd(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException, ParseException {
        Album album = WebUtils.getData(request, Album.class);
        controller.addAlbum(album);
    }

    protected void doRemove(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
        int id = WebUtils.getData(request, Integer.class);
        controller.removeAlbum(id);
    }

    protected void doUpdate(HttpServletRequest request, HttpServletResponse response) throws IOException, ParseException, SQLException {
        Album album = WebUtils.getData(request, Album.class);
        controller.updateAlbum(album);
    }

    protected void doSearch(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException, ParseException {
        String name = WebUtils.getData(request, String.class);
        WebUtils.sendData(response, model.getAlbumByName(name));
    }

    protected void doExtendedSearch(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException, ParseException {
        AlbumFilter albumFilter = WebUtils.getData(request, AlbumFilter.class);
        WebUtils.sendData(response, model.getAlbumByFilter(albumFilter));
    }
}
