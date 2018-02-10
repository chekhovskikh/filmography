package music.util;

/*import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;*/
import entities.*;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class EntityUtils {
    public static final String PATH_XML_FILES = "/";
    public static final SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
    public static final SimpleDateFormat defaultFormatDate = new SimpleDateFormat("dd.MM.yyyy");
    public static final SimpleDateFormat formatFull = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    public static final SimpleDateFormat formatTime = new SimpleDateFormat("mm:ss");
    //public static final Gson gson = new GsonBuilder().setPrettyPrinting().setDateFormat("yyyy-dd-MM hh:mm:ss").create();

    private EntityUtils() {
    }

    public static Date defaultParseDate(String source) throws ParseException {
        return defaultFormatDate.parse(source);
    }

    public static String defaultFormatDate(Date date) {
        return defaultFormatDate.format(date);
    }

    public static Date parseFullDate(String source) throws ParseException {
        return formatFull.parse(source);
    }

    public static String formatDate(Date date) {
        return formatDate.format(date);
    }

    public static Date parseDate(String date) throws ParseException {
        return formatDate.parse(date);
    }

    public static String formatTime(Date time) {
        return formatTime.format(time);
    }

    public static Date parseTime(String time) throws ParseException {
        return formatTime.parse(time);
    }



    /*public static <T extends Entity> T deserialize(String jsonObject, Type classOfObject) {
        return gson.fromJson(jsonObject, classOfObject);
    }

    public static String serialize(Entity entity) {
        return gson.toJson(entity);
    }*/

    /*public static <T extends List<E>, E extends Entity> T deserializeList(String jsonObject, Type classOfObject) {
        Type type = null;
        if (classOfObject == Band.class)
            type = new TypeToken<List<Band>>() {
            }.getType();
        else if (classOfObject == Album.class)
            type = new TypeToken<List<Album>>() {
            }.getType();
        else if (classOfObject == Genre.class)
            type = new TypeToken<List<Genre>>() {
            }.getType();
        else if (classOfObject == Song.class)
            type = new TypeToken<List<Song>>() {
            }.getType();
        return gson.fromJson(jsonObject, type);
    }*/

    /*public static <T extends Entity> String serializeList(List<T> entities) {
        return gson.toJson(entities);
    }*/

    public static void serializeToXML(Entity entity) {
        Document xmlDoc = new Document();
        if (entity.getClass() == Genre.class) {
            Genre genre = (Genre) entity;
            Element root = new Element(entity.getClass().getName());
            xmlDoc.setRootElement(root);

            Element id = new Element("id");
            id.addContent(String.valueOf(genre.getId()));
            root.addContent(id);

            Element name = new Element("name");
            name.addContent(genre.getName());
            root.addContent(name);

            Element parentId = new Element("parentId");
            parentId.addContent(String.valueOf(genre.getParentId()));
            root.addContent(parentId);
        }

        try {
            // Получаем "красивый" формат для вывода XML
            // с переводами на новую строку и отступами
            Format fmt = Format.getPrettyFormat();

            // Выводим созданный XML как поток байт на стандартный
            // вывод и в файл, используя подготовленный формат
            XMLOutputter serializer = new XMLOutputter(fmt);
            //serializer.output(xmlDoc, System.out);
            if (entity.getClass() == Genre.class) {
                Genre genre = (Genre) entity;
                serializer.output(xmlDoc, new FileOutputStream(new File(PATH_XML_FILES + genre.getName() + ".xml")));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void deSerializeFromXML(Entity entity) {
        SAXBuilder parser = new SAXBuilder();
        Document xmlDoc;

        try {
            if (entity.getClass() == Genre.class) {
                Genre genre = (Genre) entity;
                xmlDoc = parser.build(new File(PATH_XML_FILES + genre.getName() + ".xml"));
                System.out.println("Genre:");
                Element element = xmlDoc.getRootElement();
                String id = element.getChildText("id");
                String name = element.getChildText("name");
                String parentId = element.getChildText("parentId");

                System.out.println(id + ": " + name + " - " + parentId);
            }
        } catch (JDOMException | IOException e) {
            e.printStackTrace();
        }
    }

    /*public static Entity deserializeFromXML(String path) {
        SAXBuilder builder = new SAXBuilder();
        File xmlFile = new File(path);
        Entity entity = null;
        try {
            Document document = builder.build(xmlFile);
            Element rootNode = document.getRootElement();
            String nameClass = rootNode.getSelectedName();

            if (nameClass.equals(Genre.class.getSelectedName()))
                entity = parseGenre(rootNode);
            else if (nameClass.equals(Band.class.getSelectedName()))
                entity = parseBand(rootNode);
            else if (nameClass.equals(Album.class.getSelectedName()))
                entity = parseAlbum(rootNode);
            else if (nameClass.equals(Song.class.getSelectedName()))
                entity = parseSong(rootNode);
        } catch (IOException | JDOMException | ParseException e) {
            System.out.println(e.getMessage());
        }
        return entity;
    }*/

    /*private static Entity parseGenre(Element rootNode) {
        int id = Integer.parseInt(rootNode.getChildText("id"));
        String name = rootNode.getChildText("name");
        int parentId = Integer.parseInt(rootNode.getChildText("parentId"));
        return new Genre(id, name, parentId);
    }*/

    /*private static Entity parseBand(Element rootNode) throws ParseException {
        int id = Integer.parseInt(rootNode.getChildText("id"));
        String name = rootNode.getChildText("name");
        Date foundation = parseDate(rootNode.getChildText("foundation"));
        return new Band(id, name, foundation);
    }

    private static Entity parseAlbum(Element rootNode) throws ParseException {
        int id = Integer.parseInt(rootNode.getChildText("id"));
        int bandId = Integer.parseInt(rootNode.getChildText("bandId"));
        String name = rootNode.getChildText("name");
        Date release = parseDate(rootNode.getChildText("release"));
        return new Album(id, name, bandId, release);
    }

    private static Entity parseSong(Element rootNode) throws ParseException {
        int id = Integer.parseInt(rootNode.getChildText("id"));
        int bandId = Integer.parseInt(rootNode.getChildText("bandId"));
        int albumId = Integer.parseInt(rootNode.getChildText("albumId"));
        int genreId = Integer.parseInt(rootNode.getChildText("genreId"));
        String name = rootNode.getChildText("name");
        Date time = parseTime(rootNode.getChildText("time"));
        return new Song(id, name, time, bandId, albumId, genreId);
    }*/
}
