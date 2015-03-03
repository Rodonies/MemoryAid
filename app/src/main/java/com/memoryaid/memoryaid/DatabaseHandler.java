package com.memoryaid.memoryaid;

import android.app.Activity;
import android.text.format.Time;
import android.util.Log;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class DatabaseHandler extends Activity {

    static Document Doc;
    static String Path;
    static java.io.File File;

    private String filesFolder = getApplicationContext().getFilesDir().getPath();
    private String KEY_ROOT = "root";
    private String KEY_ID = "selected";
    private String KEY_PROFILE = "profile";
    private String KEY_INFO = "info";
    private String KEY_CONTACT = "contact";
    private String KEY_NAME = "name";
    private String KEY_PASSWORD = "password";
    private String KEY_RELATION = "relation";
    private String KEY_DATE = "birthday";

    public DatabaseHandler(String path) {
        Log.e("Files", "Path: " + path);
        Path = path;
        File = new File(path);
        Log.e("Files", "absPath: " + File.getAbsolutePath());
        if (!File.exists()) ResetDatabase();

        try {
            InputStream stream = new FileInputStream(File);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

            byte buf[] = new byte[2048];
            int len;
            while ((len = stream.read(buf)) != -1) {
                outputStream.write(buf, 0, len);
            }
            outputStream.close();
            stream.close();

            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            InputSource is = new InputSource();
            is.setCharacterStream(new StringReader(outputStream.toString()));
            Doc = db.parse(is);
        } catch (Exception e) {
            Log.e("XmlDataHandling", e.getMessage());
        }
    }

    public void AddContact(Profile profile, Contact contact) {
        try {
            Element prof = Doc.getElementById(Doc.getElementsByTagName(KEY_ID).item(0).getAttributes().item(0).getTextContent());
            Element cont = Doc.createElement("contact");
            prof.appendChild(cont);

            Element n = Doc.createElement("name");
            n.appendChild(Doc.createTextNode(contact.Name));
            cont.appendChild(n);

            Element r = Doc.createElement("relation");
            r.appendChild(Doc.createTextNode(contact.Relation));
            cont.appendChild(r);

            Element b = Doc.createElement("birthday");
            b.appendChild(Doc.createTextNode(contact.Birthday));
            cont.appendChild(b);

            profile.Contacts.add(contact);
            Save();
        } catch (Exception e) {
            Log.e("AddContact", e.getMessage());
        }
    }

    public void AddProfile(Profile prof) {
        int highestid = 0;
        NodeList profiles = Doc.getElementsByTagName(KEY_PROFILE);

        for (int i = 0; i < profiles.getLength(); i++) {
            int id = Integer.parseInt(profiles.item(i).getAttributes().item(0).getTextContent());
            if (highestid < id) highestid = id;
        }

        new File(filesFolder + (highestid + 1) + "_" + prof.Name + "/").mkdirs();

        try {
            Element profile = Doc.createElement("profile");
            Attr attr = Doc.createAttribute("id");
            attr.setValue(highestid + 1 + "");
            profile.setAttributeNode(attr);
            Doc.getElementsByTagName(KEY_ROOT).item(0).appendChild(profile);

            Doc.getElementsByTagName(KEY_ID).item(0).getAttributes().item(0).setTextContent(highestid + 1 + "");

            Element info = Doc.createElement("info");
            profile.appendChild(info);

            Element name = Doc.createElement("name");
            name.appendChild(Doc.createTextNode(prof.Name));
            info.appendChild(name);

            Element password = Doc.createElement("password");
            password.appendChild(Doc.createTextNode(prof.Password));
            info.appendChild(password);

            Element birthday = Doc.createElement("birthday");
            birthday.appendChild(Doc.createTextNode(prof.Birthday));
            info.appendChild(birthday);

            Save();
        } catch (Exception e) {
            Log.e("AddProfile", e.getMessage());
        }
    }

    public void DeleteProfile(int id) {
        Element profile = Doc.getElementById(Integer.toString(id));
        Log.e("DeleteProfile", "" + profile.getNodeName());
    }

    public Profile GetProfile() {
        String name = null;
        String password = null;
        String birthday = null;
        List<Contact> list = new ArrayList<Contact>();

        try {
            Element profile = Doc.getElementById(Doc.getElementsByTagName(KEY_ID).item(0).getAttributes().item(0).getTextContent());
            Element info = (Element) profile.getElementsByTagName(KEY_INFO).item(0);
            name = getValue(info, KEY_NAME);
            password = getValue(info, KEY_PASSWORD);
            birthday = getValue(info, KEY_DATE);

            NodeList contacts = profile.getElementsByTagName(KEY_CONTACT);

            for (int i = 0; i < contacts.getLength(); i++) {
                Element contact = (Element) contacts.item(i);
                list.add(new Contact(getValue(contact, KEY_NAME), getValue(contact, KEY_RELATION), getValue(contact, KEY_DATE)));
            }
        } catch (Exception e) {
            Log.e("GetProfile", e.getMessage());
        }

        return new Profile(name, password, birthday, list);
    }

    public static String getValue(Element item, String str) {
        NodeList n = item.getElementsByTagName(str);
        return getElementValue(n.item(0));
    }

    private static String getElementValue(Node elem) {
        Node child;
        if (elem != null) {
            if (elem.hasChildNodes()) {
                for (child = elem.getFirstChild(); child != null; child = child.getNextSibling()) {
                    if (child.getNodeType() == Node.TEXT_NODE) {
                        return child.getNodeValue();
                    }
                }
            }
        }
        return "";
    }

    private void Save() {
        try {
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();

            //Useless formatting voor debugging
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");

            transformer.transform(new DOMSource(Doc), new StreamResult(File));
        } catch (Exception e) {
            Log.e("Save", e.getMessage());
        }
    }

    public void ResetDatabase() {
        DeleteFiles(filesFolder);
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

            Document doc = docBuilder.newDocument();

            Element root = doc.createElement("root");
            doc.appendChild(root);

            Element selected = doc.createElement("selected");
            Attr attr = doc.createAttribute("value");
            attr.setValue("0");
            selected.setAttributeNode(attr);
            root.appendChild(selected);

            Element profile = doc.createElement("profile");
            attr = doc.createAttribute("id");
            attr.setValue("0");
            profile.setAttributeNode(attr);
            root.appendChild(profile);

            new File(filesFolder + "0_default/").mkdirs();

            Element info = doc.createElement("info");
            profile.appendChild(info);

            /*--------------------------------------------------------------*/
            Element name = doc.createElement("name");
            name.appendChild(doc.createTextNode("default"));
            info.appendChild(name);

            Element password = doc.createElement("password");
            password.appendChild(doc.createTextNode("default"));
            info.appendChild(password);

            Element birthday = doc.createElement("birthday");
            Time today = new Time(Time.getCurrentTimezone());
            birthday.appendChild(doc.createTextNode(today.year + ""));
            info.appendChild(birthday);
            /*--------------------------------------------------------------*/

            for (int i = 0; i < 2; i++) {
                Element contact = doc.createElement("contact");
                profile.appendChild(contact);

                Element n = doc.createElement("name");
                n.appendChild(doc.createTextNode("Jos"));
                contact.appendChild(n);

                Element r = doc.createElement("relation");
                r.appendChild(doc.createTextNode("Jef"));
                contact.appendChild(r);

                Element b = doc.createElement("birthday");
                b.appendChild(doc.createTextNode(today.year + ""));
                contact.appendChild(b);
            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();

            Doc = doc;
            Save();
        } catch (Exception e) {
            Log.e("ResetDatabase", e.getMessage());
        }
    }

    public void ShowFile() {
        try {
            BufferedReader br = new BufferedReader(new FileReader(File));
            String line;

            while ((line = br.readLine()) != null) {
                Log.e("ShowFile", "" + line);
            }
            br.close();
        } catch (IOException e) {
            Log.e("ShowFile", e.getMessage());
        }
    }

    public void DeleteFiles(String path) {
        File f = new File(path);
        File file[] = f.listFiles();
        for (int i = 0; i < file.length; i++) {
            file[i].delete();
        }
    }
}