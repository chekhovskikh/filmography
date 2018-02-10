package servlets;

import entities.Song;
import entities.filters.SongFilter;
import servlets.util.WebUtils;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

public class SongServlet extends MusicServlet {

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        if (WebUtils.isAjax(request)) {
            try {
                int id = Integer.parseInt(request.getParameter("id"));
                WebUtils.sendData(response, model.getSong(id));
            } catch (Exception e) {
                WebUtils.sendBadRequest(response, e.getCause().getMessage());
            }
            return;
        }

        List<Song> songs = null;
        Song selectedSong = null;
        String genreName = null;
        String bandName = null;
        String albumName = null;
        try {
            songs = model.getSongs();
            if (request.getParameter("id") != null) {
                int id = Integer.parseInt(request.getParameter("id"));
                selectedSong = model.getSong(id);

                if (selectedSong.getGenreId() != 0)
                    genreName = model.getGenre(selectedSong.getGenreId()).getName();
                if (selectedSong.getBandId() != 0)
                    bandName = model.getBand(selectedSong.getBandId()).getName();
                if (selectedSong.getAlbumId() != 0)
                    albumName = model.getAlbum(selectedSong.getAlbumId()).getName();
            }
        } catch (SQLException | ParseException | NumberFormatException e) {
            error(request, response,  "/songs", e.getMessage());
        }
        request.setAttribute("selectedSong", selectedSong);
        request.setAttribute("genreName", genreName);
        request.setAttribute("bandName", bandName);
        request.setAttribute("albumName", albumName);
        request.setAttribute("songs", songs);
        request.getRequestDispatcher("songs.jsp").forward(request, response);
    }

    protected void doAdd(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException, ParseException {
        Song song = WebUtils.getData(request, Song.class);
        controller.addSong(song);
    }

    protected void doRemove(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
        int id = WebUtils.getData(request, Integer.class);
        controller.removeSong(id);
    }

    protected void doUpdate(HttpServletRequest request, HttpServletResponse response) throws IOException, ParseException, SQLException {
        Song song = WebUtils.getData(request, Song.class);
        controller.updateSong(song);
    }

    protected void doSearch(HttpServletRequest request, HttpServletResponse response) throws IOException, ParseException, SQLException {
        String name = WebUtils.getData(request, String.class);
        WebUtils.sendData(response, model.getSongByName(name));
    }

    protected void doExtendedSearch(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException, ParseException {
        SongFilter songFilter = WebUtils.getData(request, SongFilter.class);
        WebUtils.sendData(response, model.getSongByFilter(songFilter));
    }
}

