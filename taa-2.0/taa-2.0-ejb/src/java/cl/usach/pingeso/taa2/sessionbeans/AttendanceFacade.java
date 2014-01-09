/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.usach.pingeso.taa2.sessionbeans;

import cl.usach.pingeso.taa2.entityclasses.Attendance;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import static com.googlecode.javacv.cpp.opencv_highgui.*;
import static com.googlecode.javacv.cpp.opencv_core.*;
import static com.googlecode.javacv.cpp.opencv_imgproc.*;
import static com.googlecode.javacv.cpp.opencv_contrib.*;
import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.NoResultException;
import javax.persistence.Query;

/**
 *
 * @author Nico
 */
@Stateless
public class AttendanceFacade extends AbstractFacade<Attendance> implements AttendanceFacadeLocal {
    @PersistenceContext(unitName = "taa-2.0-ejbPU")
    private EntityManager em;
    
    private FaceRecognizer faceRecognizer = createEigenFaceRecognizer();
    //private FaceRecognizer faceRecognizer = createFisherFaceRecognizer();
    //private FaceRecognizer faceRecognizer = createLBPHFaceRecognizer()
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AttendanceFacade() {
        super(Attendance.class);
    }
    
    @Override
    public void train(String rutStudent) {
        String trainingDir = "C:\\photos_taa\\real";
        File root = new File(trainingDir);
        File[] ficheros = root.listFiles();
        int total = 0;
        ArrayList<File[]> temp = new ArrayList();
        for (int x=0;x<ficheros.length;x++)
        {
            FilenameFilter pngFilter = new FilenameFilter()
            {
                @Override
                public boolean accept(File dir, String name) {
                    return name.toLowerCase().endsWith(".png");
                }
            };
            File[] imageFiles = ficheros[x].listFiles(pngFilter);
            temp.add(imageFiles);
            total = total + imageFiles.length;
            //System.out.println(ficheros[x].getName());
        }
        //System.out.println(total);
        MatVector images = new MatVector(total);
        int[] labels = new int[total];
        int counter = 0;
        int label;
        IplImage img;
        IplImage grayImg;
        for(File[] imageFiles : temp)
        {
            for (File image : imageFiles) {
                img = cvLoadImage(image.getAbsolutePath());
                System.out.println(image.getName());
                label = Integer.parseInt((image.getName().split("\\.")[0]).split("\\-")[1]);
                grayImg = IplImage.create(img.width(), img.height(), IPL_DEPTH_8U, 1);
                cvCvtColor(img, grayImg, CV_BGR2GRAY);
                images.put(counter, grayImg);
                labels[counter] = label;
                counter++;
            }
        }
        faceRecognizer.train(images, labels);
    }
    
    @Override
    public void photoValidation(String photo, int[] id, double[] distance) {
        IplImage testImage = cvLoadImage(photo);
        IplImage greyTestImage = IplImage.create(testImage.width(), testImage.height(), IPL_DEPTH_8U, 1);
        cvCvtColor(testImage, greyTestImage, CV_BGR2GRAY);
        faceRecognizer.predict(greyTestImage, id, distance);
    }
    
    @Override
    public boolean predict(String photo, String rutStudent) {
        int rutInt = Integer.parseInt(rutStudent);
        int [] id_test = {-1};
        double[] distance = {0.0};
        double distance_min = 10000;
        this.train(rutStudent);
        this.photoValidation(photo, id_test, distance);
        System.out.println("Label:");
        System.out.println(id_test[0]);
        System.out.println("Confidence:");
        System.out.println(distance[0]);
        if (distance[0] < distance_min && rutInt == id_test[0])
        {
            System.out.println("Reconocido");
            return true;
        }
        else
        {
            System.out.println("No reconocido");
            return false;
        }
    }
    
    @Override
    public Attendance findByCourseCode(Object courseCode) {
        Query q0 = this.em.createNamedQuery("Attendance.findByCourseCode");
        q0.setParameter("courseCode", courseCode);
        Attendance res;
        try {
            res = (Attendance)q0.getSingleResult();
        }
        catch (NoResultException nre) {
            return null;
        }
        return res;
    }

    @Override
    public List<Date> findAllDatesByCourse(String course) {
        Query q0 = this.em.createNamedQuery("Attendance.findAllDatesByCourseCode");
        q0.setParameter("courseCode", course);
        List<Date> res;
        try {
            res = (List<Date>)q0.getResultList();
        }
        catch (NoResultException nre) {
            return null;
        }
        return res;
    }
    
    @Override
    public int removeAll(Date attendanceDate, String courseCode) {
        Query q0 = this.em.createNamedQuery("Attendance.removeAll");
        q0.setParameter("attendanceDate", attendanceDate);
        q0.setParameter("courseCode", courseCode);
        int res;
        try {
            res = (int)q0.executeUpdate();
        }
        catch (NoResultException nre) {
            return 0;
        }
        return res;
    }
}