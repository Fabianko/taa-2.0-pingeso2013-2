/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.usach.pingeso.taa2.utils;

import cl.usach.pingeso.taa2.entityclasses.Student;
import java.util.List;
import javax.faces.model.ListDataModel;
import org.primefaces.model.SelectableDataModel;

/**
 *
 * @author Diego
 */
public class StudentDataModel extends ListDataModel<Student> implements SelectableDataModel<Student> {    
  
    public StudentDataModel() {  
    }  
  
    public StudentDataModel(List<Student> data) {  
        super(data);  
    }  
      
    @Override  
    public Student getRowData(String rowKey) {    
        List<Student> students = (List<Student>) getWrappedData();  
          
        for(Student student : students) {  
            if(student.getRut().equals(rowKey))  
                return student;  
        }  
        return null;  
    }  
  
    @Override  
    public Object getRowKey(Student student) {  
        return student.getRut();  
    }  
}