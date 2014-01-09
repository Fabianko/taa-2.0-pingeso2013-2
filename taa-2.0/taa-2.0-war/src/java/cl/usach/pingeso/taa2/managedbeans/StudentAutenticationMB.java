/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.usach.pingeso.taa2.managedbeans;

import cl.usach.pingeso.taa2.entityclasses.Student;
import static cl.usach.pingeso.taa2.managedbeans.AutenticationStudents.detect;
import cl.usach.pingeso.taa2.utils.General;
import com.googlecode.javacv.cpp.opencv_core;
import static com.googlecode.javacv.cpp.opencv_core.CV_AA;
import static com.googlecode.javacv.cpp.opencv_core.cvClearMemStorage;
import static com.googlecode.javacv.cpp.opencv_core.cvGetSeqElem;
import static com.googlecode.javacv.cpp.opencv_core.cvLoad;
import static com.googlecode.javacv.cpp.opencv_core.cvPoint;
import static com.googlecode.javacv.cpp.opencv_core.cvRectangle;
import static com.googlecode.javacv.cpp.opencv_highgui.cvLoadImage;
import com.googlecode.javacv.cpp.opencv_objdetect;
import static com.googlecode.javacv.cpp.opencv_objdetect.CV_HAAR_DO_CANNY_PRUNING;
import static com.googlecode.javacv.cpp.opencv_objdetect.cvHaarDetectObjects;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.FacesException;
import javax.faces.application.FacesMessage;
import javax.imageio.stream.FileImageOutputStream;
import org.primefaces.event.CaptureEvent;

/**
 *
 * @author Diego
 */
@Named(value = "studentAutenticationMB")
@RequestScoped
public class StudentAutenticationMB {
    
    /**
     * Creates a new instance of StudentAutenticationMB
     */
    
    String currentPhoto;
    String nomTemp;
    
    public StudentAutenticationMB() {
    }
        
    public String oncapture(CaptureEvent captureEvent)
    {
        //System.out.println(studentRut);
        final byte[] datos = captureEvent.getData();
        Random r = new Random(System.currentTimeMillis());
        int val = r.nextInt(10000)+1;
        this.currentPhoto = String.valueOf(val)+".png";
        final String filePhoto = "C:\\photos_taa\\temp\\"+currentPhoto;
        System.out.println(filePhoto);
        FileImageOutputStream outputStream;
            
        try {
            outputStream = new FileImageOutputStream(new File(filePhoto));
            outputStream.write(datos, 0, datos.length);
            String evaluation = detectFace();
            if(evaluation.compareTo("ok")==0)
            {
                System.out.println("Fotografía guardada satisfactoriamente !!!");
                General.viewMessage(FacesMessage.SEVERITY_INFO,
                "Fotografía guardada satisfactoriamente !!!",
                "Fotografía guardada satisfactoriamente !!!");
                outputStream.close();
                return currentPhoto;
            }
            else if(evaluation.compareTo("noFaces")==0)
            {
                System.out.println("No se detectaron rostros en la fotografía.");
                General.viewMessage(FacesMessage.SEVERITY_INFO,
                "No se detectaron rostros en la fotografía.",
                "No se detectaron rostros en la fotografía.");
                outputStream.close();
                return "";
            }
            else if(evaluation.compareTo("manyFaces")==0)
            {
                System.out.println("Se detectó más de un rostro en la fotografía.");
                General.viewMessage(FacesMessage.SEVERITY_INFO,
                "Se detectó más de un rostro en la fotografía.",
                "Se detectó más de un rostro en la fotografía.");
                outputStream.close();
                return "";
            }
            else if(evaluation.compareTo("smallFace")==0)
            {
                System.out.println("Rostro demasiado lejos.");
                General.viewMessage(FacesMessage.SEVERITY_INFO,
                "Rostro demasiado lejos. Acercate a la cámara",
                "Rostro demasiado lejos. Acercate a la cámara");
                outputStream.close();
                return "";
            }
            else if(evaluation.compareTo("bigFace")==0)
            {
                System.out.println("Rostro demasiado cerca.");
                General.viewMessage(FacesMessage.SEVERITY_INFO,
                "Rostro demasiado cerca. Alejate de la cámara",
                "Rostro demasiado cerca. Alejate de la cámara");
                outputStream.close();
                return "";
            }
            else
            {
                System.out.println("Rostro demasiado cerca.");
                General.viewMessage(FacesMessage.SEVERITY_INFO,
                "Error de causa desconocida. Contacta al administrador del sistema.",
                "Error de causa desconocida. Contacta al administrador del sistema.");
                System.out.println("Error de causa desconocida.");
                outputStream.close();
                return "";
            }
        }
        catch (IOException e)
        {
            throw new FacesException("Error al guardar fotografía", e);
        }
    }
    
    private String detectFace()
    {
        opencv_core.IplImage img = cvLoadImage("C:\\photos_taa\\temp\\"+currentPhoto);
        String result = detect(img);
        System.out.println(result);
        return result;
    }
    
    public static String detect(opencv_core.IplImage src)
    {
        opencv_objdetect.CvHaarClassifierCascade cascade = new opencv_objdetect.CvHaarClassifierCascade(cvLoad("C:\\opencv\\data\\haarcascades\\haarcascade_frontalface_alt.xml"));
        opencv_core.CvMemStorage storage = opencv_core.CvMemStorage.create();
	opencv_core.CvSeq sign = cvHaarDetectObjects(src, cascade, storage, 1.5, 3, CV_HAAR_DO_CANNY_PRUNING);
	cvClearMemStorage(storage);
        int total_Faces = sign.total();		
        if(total_Faces < 1)
            return "noFaces";
        else if(total_Faces > 1)
            return "manyFaces";
        else
        {
            opencv_core.CvRect r = new opencv_core.CvRect(cvGetSeqElem(sign, 0));
            cvRectangle (src, cvPoint(r.x(), r.y()), cvPoint(r.width() + r.x(), r.height() + r.y()), opencv_core.CvScalar.RED, 2, CV_AA, 0);
            if(r.width() < 100)
                return "smallFace";
            else if(r.width() > 130)
                return "bigFace";
            else
                return "ok";
        }
    }
    
    public String getCurrentPhoto() {
        return currentPhoto;
    }
    
    public void setCurrentPhoto(String photo) {
        this.currentPhoto = photo;
    }
    
    public boolean isViewPhoto() {
        return currentPhoto != null;
    }
}