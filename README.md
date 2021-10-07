## Draft code(Version 0.5)

Using only realtime database
Datastructure:
![image](https://github.com/SEG2105-uottawa/seg2105f21-project-project_gr13/blob/deliverable1_V0.5/datasturctrue.png)  
My first thought is to use "ADMIN","Customer"and "Employee" as the parents keys, but it will increase coding work.  

methods:  
   Login is to check if the passwd username and role match, and then call the callback function.  
   set a new page in onSuccess callback  
   
   register is simply to check if user has a child equal to user input. If not then create a new acc.  
   
   In the radioGroup, the subclasses are alternated by the checked states.


Confusing about the Android UI. Dont know how to do it properly to interact with User class. Maybe because it is asynchrounous programing and I am still thinking in a synchrounous way.
### UML:
![image](https://github.com/SEG2105-uottawa/seg2105f21-project-project_gr13/blob/main/uml.png)

>>>>>>> deliverable1_V0.5

## Version 0.6  
![image](https://github.com/SEG2105-uottawa/seg2105f21-project-project_gr13/blob/main/datastructure2.png)   
Datastructure updated  
add a new page for users to input their information.  


