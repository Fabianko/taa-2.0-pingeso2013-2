package cl.usach.pingeso.taa2.managedbeans;

import cl.usach.pingeso.taa2.entityclasses.Photo;
import static cl.usach.pingeso.taa2.managedbeans.StudentAutenticationMB.detect;
import cl.usach.pingeso.taa2.sessionbeans.PhotoFacadeLocal;
import cl.usach.pingeso.taa2.sessionbeans.StudentFacadeLocal;
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
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import java.io.Serializable;  
import java.util.List;
import java.util.Random;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.FacesException;
import javax.faces.application.FacesMessage;
import javax.imageio.stream.FileImageOutputStream;
import javax.inject.Inject;
import org.primefaces.event.CaptureEvent;

/**
 *
 * @author Diego
 */
@Named(value = "photoMB")
@RequestScoped
public class PhotoMB implements Serializable {
    @EJB
    private StudentFacadeLocal studentFacade;
    @EJB
    private PhotoFacadeLocal photoFacade;
    private List<Photo> photoList;
    private List<Photo> photoFiltered;
    @Inject 
    private StudentConversation mantStudent;
    private String currentPhoto;
    private String photoNoExt;
    private Integer numPhotos;
    
    public PhotoMB() {
    }
    
    @PostConstruct
    public void init() {
        this.photoList = photoFacade.findByRut(mantStudent.getIdUserDetails());
        numPhotos = photoList.size();
    }
    
    public List<Photo> getPhotoList() {
        return photoList;
    }
    
    public void setPhotoList(List<Photo> list) {
        this.photoList = list;
    }

    public List<Photo> getPhotoFiltered() {
        return photoFiltered;
    }

    public void setPhotoFiltered(List<Photo> photoFiltered) {
        this.photoFiltered = photoFiltered;
    }
    
    public void viewPhoto(String photoCode) {
        Photo photoView = photoFacade.find(photoCode);
        if (photoView != null) {
            General.goToPage("/faces/viewPhoto.xhtml");
        }
        else {
            General.goToPage("/faces/photosStudent.xhtml?cid=".concat(this.mantStudent.getConversation().getId()));
        }
    }
    
    public void addPhoto(String rutStudent) {
       General.goToPage("/faces/photoAdd.xhtml?cid=".concat(this.mantStudent.getConversation().getId()));  
    }
    
    public void deletePhoto(String photoCode) {
        Photo temp = photoFacade.find(Long.parseLong(photoCode));
        boolean resultado;
        try
        {
            File photoDel=new File("C:\\photos_taa\\real\\"+mantStudent.getIdUserDetails()+"\\"+temp.getPhotoPath());
            photoDel.delete();
            photoFacade.remove(temp);
            resultado = true;
        }
        catch(Exception e)
        {
            resultado = false;
        }
        
        if (resultado) {
            General.viewMessage(FacesMessage.SEVERITY_INFO,
                    "Se ha eliminado satisfactoriamente la fotografía.",
                    "Se ha eliminado satisfactoriamente la fotografía.");
        } else {
            General.viewMessage(FacesMessage.SEVERITY_ERROR,
                    "No es posible eliminar la fotografía en estos momentos.",
                    "No es posible eliminar la fotografía en estos momentos.");
        }
        General.goToPage("/faces/photosStudent.xhtml?cid=".concat(this.mantStudent.getConversation().getId()));
    }
    
    public void backToStudents() {
       General.goToPage("/faces/studentList.xhtml");    
    }
    
    public void oncapture(CaptureEvent captureEvent)
    {
        final byte[] datos = captureEvent.getData();
        Random r = new Random(System.currentTimeMillis());
        int val = r.nextInt(10000)+1;
        this.photoNoExt = String.valueOf(val);
        this.currentPhoto = photoNoExt+".png";
        final String filePhoto = "C:\\photos_taa\\temp\\"+currentPhoto;
        FileImageOutputStream outputStream;
        
        try {
            outputStream = new FileImageOutputStream(new File(filePhoto));
            outputStream.write(datos, 0, datos.length);
            outputStream.close();
            String evaluation = detectFace();
            if(evaluation.compareTo("ok")==0)
            {
                File forig =new File(filePhoto); 
                File fdest=new File("C:\\photos_taa\\real\\"+mantStudent.getIdUserDetails()+"\\"+photoNoExt+"-"+mantStudent.getIdUserDetails()+".png");
                System.out.println(fdest);
                forig.renameTo(fdest);
                Photo newPhoto = new Photo();
                newPhoto.setPhotoPath(photoNoExt+"-"+mantStudent.getIdUserDetails()+".png");
                newPhoto.setPhotoState("1");
                newPhoto.setRut(studentFacade.find(mantStudent.getIdUserDetails()));
                photoFacade.create(newPhoto);
                System.out.println("Fotografía guardada satisfactoriamente !!!");
                General.viewMessage(FacesMessage.SEVERITY_INFO,
                "Fotografía validada y almacenada satisfactoriamente !!!",
                "Fotografía validada y almacenada satisfactoriamente !!!");
                General.goToPage("/faces/photosStudent.xhtml?cid=".concat(this.mantStudent.getConversation().getId()));
            }
            else if(evaluation.compareTo("noFaces")==0)
            {
                File forig =new File(filePhoto);
                forig.delete();
                System.out.println("No se detectaron rostros en la fotografía.");
                General.viewMessage(FacesMessage.SEVERITY_INFO,
                "No se detectó un rostro en la fotografía. Inténtalo nuevamente.",
                "No se detectó un rostro en la fotografía. Inténtalo nuevamente.");
                General.goToPage("/faces/photosStudent.xhtml?cid=".concat(this.mantStudent.getConversation().getId()));
            }
            else if(evaluation.compareTo("manyFaces")==0)
            {
                File forig =new File(filePhoto);
                forig.delete();
                System.out.println("Se detectó más de un rostro en la fotografía.");
                General.viewMessage(FacesMessage.SEVERITY_INFO,
                "Se detectó más de un rostro en la fotografía. Inténtalo nuevamente.",
                "Se detectó más de un rostro en la fotografía. Inténtalo nuevamente.");
                General.goToPage("/faces/photosStudent.xhtml?cid=".concat(this.mantStudent.getConversation().getId()));
            }
            else if(evaluation.compareTo("smallFace")==0)
            {
                File forig =new File(filePhoto);
                forig.delete();
                System.out.println("Rostro demasiado lejos.");
                General.viewMessage(FacesMessage.SEVERITY_INFO,
                "Rostro demasiado lejos. Acercate a la cámara e inténtalo nuevamente.",
                "Rostro demasiado lejos. Acercate a la cámara e Inténtalo nuevamente.");
                General.goToPage("/faces/photosStudent.xhtml?cid=".concat(this.mantStudent.getConversation().getId()));
            }
            else if(evaluation.compareTo("bigFace")==0)
            {
                File forig =new File(filePhoto);
                forig.delete();
                System.out.println("Rostro demasiado cerca.");
                General.viewMessage(FacesMessage.SEVERITY_INFO,
                "Rostro demasiado cerca. Alejate de la cámara e inténtalo nuevamente.",
                "Rostro demasiado cerca. Alejate de la cámara e inténtalo nuevamente.");
                General.goToPage("/faces/photosStudent.xhtml?cid=".concat(this.mantStudent.getConversation().getId()));
            }
            else
            {
                File forig =new File(filePhoto);
                forig.delete();
                System.out.println("Rostro demasiado cerca.");
                General.viewMessage(FacesMessage.SEVERITY_INFO,
                "Error de causa desconocida. Contacta al administrador del sistema.",
                "Error de causa desconocida. Contacta al administrador del sistema.");
                System.out.println("Error de causa desconocida.");
                General.goToPage("/faces/photosStudent.xhtml?cid=".concat(this.mantStudent.getConversation().getId()));
            }
        }
        catch (IOException e)
        {
            File forig =new File(filePhoto);
            forig.delete();
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
    
    public boolean maxPhotos()
    {
        if(this.numPhotos < 10)
            return true;
        return false;
    }
}