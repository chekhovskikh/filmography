import com.google.inject.Injector;
import entities.Band;
import music.*;
import music.guice.InjectorBuilder;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.Date;

public class Main {
    public static void main(String[] args) throws SQLException, IOException, ParseException, ExecutionException, InterruptedException {
        InputStream inputProperties = new FileInputStream("src/main/resources/oracle.properties");
        Properties properties = new Properties();
        properties.load(inputProperties);

        Injector injector = new InjectorBuilder(properties).getInjector();
        Controller controller = injector.getInstance(Controller.class);

        Band band = new Band();
        band.setFoundation(new Date());

        System.out.println(controller.getModel().getAlbums());


        /*Genre genre = (Genre)genreDAO.get(2);
        System.out.println(genre);*/

        //Genre genre1 = new Genre("Popsa",58);
        //List<Genre> genres = (List<Genre>) genreDAO.getAll();
        //System.out.println(genre1);
        //genreDAO.add(genre1);
        /*Genre genre2 = new Genre(59,"lalala",1);
        genreDAO.update(genre2);
        System.out.println(genre1);
        System.out.println(genre2);*/
        /*Band band1 = new Band(1,"Ivan", EntityUtils.parseDate("2001-01-01"));
        Band band2 = new Band(2,"Vlad", EntityUtils.parseDate("2002-02-02"));
        List<Band> list = new ArrayList<>();
        list.add(band1);
        list.add(band2);
        String s = EntityUtils.serializeList(list);

        System.out.print(s);
        List<Band> list2 = EntityUtils.deserializeList(s, Band.class);
        Band band3 = list2.get(0);
        System.out.println(band3);
        System.out.println("-----------------");
        Type type = new TypeToken<List<Entity>>(){}.getType();

        Genre genre = new Genre(1,"Rock", 0);
        EntityUtils.serializeToXML(genre);

        System.out.println("-----------------");
        EntityUtils.deSerializeFromXML(genre);
        */

        /*
        System.out.println("-----------------------------");
        String name = "my";
        songs = ms.searchSongs(name);
        System.out.println(songs);*/

        //ms.addBands(s);
        //ms.removeBand(1);
        //ms.searchBands("Ivan");
        //String first = ms.getAllBands();
        //String first = ms.getBand(2);
        //EntityUtils.deserializeList(first,Band.class);
        //System.out.println(first);
        /*for (Entity elem : first) {
            Band printElem = (Band)elem;
            System.out.println(printElem.getSelectedName());
        }*/
        //ms.removeAllBands();
        /*List<Entity> second = ms.searchBands("Ivan");
        for (Entity elem : second) {
            Band printElem = (Band)elem;
            System.out.println(printElem.getSelectedName());
        }*/
        //ms.close();*/
    }
}
