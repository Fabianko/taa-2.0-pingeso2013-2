/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.usach.pingeso.taa2.managedbeans;

import cl.usach.pingeso.taa2.entityclasses.Program;
import cl.usach.pingeso.taa2.entityclasses.Student;
import cl.usach.pingeso.taa2.entityclasses.User;
import cl.usach.pingeso.taa2.sessionbeans.StudentFacadeLocal;
import cl.usach.pingeso.taa2.sessionbeans.UserFacadeLocal;
import cl.usach.pingeso.taa2.utils.General;
import java.io.FileNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Named;
import javax.enterprise.context.Dependent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.primefaces.event.FileUploadEvent;
//import org.primefaces.event.FileUploadEvent;


@Named(value = "excelRegister")
@RequestScoped 
public class ExcelRegister {
    @EJB
    private UserFacadeLocal userFacade;
    @EJB
    private StudentFacadeLocal studentFacade;
    
    private String rol="Estudiante";
    private String state="0";
    private String destination="C:\\";
    private Program p;
    
    public void upload(FileUploadEvent event) {  
        FacesMessage msg = new FacesMessage("Success! ", event.getFile().getFileName() + " is uploaded.");  
        
        FacesContext.getCurrentInstance().addMessage(null, msg);
        // Do what you want with the file        
        System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAA");
        System.out.println(event.getFile().getFileName());
        try {
            copyFile(event.getFile().getFileName(), event.getFile().getInputstream());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }  

    public void copyFile(String fileName, InputStream in) {
           try {
             
             
                // write the inputStream to a FileOutputStream
                OutputStream out = new FileOutputStream(new File(destination + fileName));
             
                int read = 0;
                byte[] bytes = new byte[1024];
             
                while ((read = in.read(bytes)) != -1) {
                    out.write(bytes, 0, read);
                }
             
                in.close();
                out.flush();
                out.close();
             
                System.out.println("New file created!");
                } catch (IOException e) {
                System.out.println(e.getMessage());
                }
    }

    public void handleFileUpload(FileUploadEvent event) {
        try {
            File targetFolder = new File("C:\\Users\\Nico\\Desktop\\login");
            InputStream inputStream;
            inputStream = event.getFile().getInputstream();
            OutputStream out = new FileOutputStream(new File(targetFolder,
                    event.getFile().getFileName()));
            int read = 0;
            byte[] bytes = new byte[1024];

            while ((read = inputStream.read(bytes)) != -1) {
                out.write(bytes, 0, read);
                System.out.println(read);
            }
            inputStream.close();
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

}

    
     public void lecturaExcel() {
        try {
            // Se crea una referencia al documento excel
            HSSFWorkbook workbook;
            workbook = new HSSFWorkbook(new FileInputStream("C:/Users/Nico/Desktop/libro1.xls"));
            // indicamos la hoja que queremos leer
            HSSFSheet sheet = workbook.getSheet("Hoja1");
            //recorremos filas
            for (Row r : sheet) {
                if (r.getRowNum() != 0) {                    
                    if (r.getCell(0) != null) {
                        try {
                            StudentRegisterExcel(r);
                            for(Cell c:r){
                                if (c.getCellType() == Cell.CELL_TYPE_STRING) {
                                 System.out.println("STRING CELL--> " + c.getStringCellValue());
                                 } else if (c.getCellType() == Cell.CELL_TYPE_NUMERIC) {
                                 System.out.println("NUMERIC CELL--> " + c.getNumericCellValue());
                                 }
                            }
                            
                        } catch (Exception e) {
                            e.printStackTrace();
                            e = null;
                        }
                    }
                }
            }
            
            General.goToPage("/faces/studentsExcel.xhtml?faces-redirect=true");
            General.viewMessage(FacesMessage.SEVERITY_INFO,
                    "Alumnos registrado satisfactorimante!!!",
                    "Alumnos registrado satisfactorimante!!!");
        } catch (Exception e) {
            System.out.println("Error! " + e);
            
            General.viewMessage(FacesMessage.SEVERITY_ERROR, 
                    e.getMessage(), 
                    e.getMessage());
            General.goToPage("/faces/studentsExcel.xhtml?faces-redirect=true");
        }
    }
         public void StudentRegisterExcel(Row r){
         try {
            User newUser = new User();
            newUser.setRut(r.getCell(0).toString());
            newUser.setFirstName(r.getCell(1).toString());
            newUser.setMiddleName(r.getCell(2).toString());
            newUser.setPrimaryLastName(r.getCell(3).toString());
            newUser.setSecondLastName(r.getCell(4).toString());
            newUser.setEmail(r.getCell(5).toString());
            p.setProgramName(r.getCell(6).toString());
            newUser.setPassword(null);
            newUser.setUserState(state);
            newUser.setRol(rol);
            
            userFacade.create(newUser);
            studentRegisterNow1(newUser,p);
        }
        catch (Exception e) {
        }
    }
    public void studentRegisterNow1(User u, Program p){
            System.out.println("AAAAAAAAAAAAAAAAA");
            Student newStudent=new Student();
            newStudent.setRut(u.getRut());
            newStudent.setUser(u);
            newStudent.setProgramCode(p);
            studentFacade.create(newStudent);
            System.out.println("BBBBBBBBBBBBBBBb");
    }
}


