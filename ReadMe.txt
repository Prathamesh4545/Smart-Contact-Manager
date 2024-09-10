Smart Contact Manager

Introduction 

In today’s fast-paced world, managing personal and professional connections efficiently is crucial. Introducing the Smart Contact Manager (SCM)—a powerful tool designed to simplify and enhance the way you manage your contacts. With a user-centric approach and a range of robust features, SCM is more than just a contact list; it’s your gateway to seamless and intuitive contact management.

Key Features:

User Signup and Authentication: Effortlessly create an account with your email and password, or opt for quick sign-ups using Google or GitHub. Enjoy enhanced security with email verification to ensure the integrity of your account.

Contact Management: Add contacts with ease, including their pictures for a more personalized touch. Upload these images to the cloud using AWS or Cloudinary for secure and scalable storage.

Comprehensive View: Browse through all your contacts with a user-friendly interface. View detailed information, update entries, and delete contacts as needed.

Communication Made Easy: Compose and send emails directly from SCM, complete with text and attachments. Streamline your communication without leaving the application.

Advanced Search and Organization: Quickly find specific contacts with powerful search functionality and pagination for efficient navigation. Export your contact data to Excel for offline access and organization.

Personalization and Usability: Mark your favorite contacts for quick access and customize your experience with both dark and light themes to suit your preference.

Profile Management: View and edit your own profile details effortlessly, ensuring that your information is always up to date.

Feedback and Improvement: Share your thoughts and suggestions to help us continuously improve SCM and meet your needs more effectively.

The Smart Contact Manager is designed to provide a seamless and efficient contact management experience, combining convenience with powerful features to help you stay organized and connected. Whether for personal use or professional needs, SCM is here to make your contact management smarter and more intuitive.



Important Notes : 

Step 1. If any pages wants Tailwind CSS :  
        npx tailwindcss -i ./src/main/resources/static/css/input.css -o ./src/main/resources/static/css/output.css --watch

Step 2. include output.css in your html : 
        <link rel="stylesheet" data-th-href="@{'/css/output.css'}">

Step 3. If you want Flowbite: include CSS and JavaScript using cdn



Features of SCM

1. User Signup with email and password
2. Verify account using email verification
3. User signup with google and GitHub
4. Add contact with picture
5. contact picture upload to cloud[AWS, Cloudinary]
6. User can view all contact
7. Can view contact details
8. Compose and send email for directly from SCM
9. Email contain text + attachment
10. Update the contact
11. Delete the contact
12. Search the contact
13. Pagination
14. Export contact data to excel
15. Mark favorite contact
16. See and Edit own profile details
17. Dark and light theme
18. Provide feedback

Technologies

1. Latest Spring boot
2. Spring Framework
3. Spring MVC
4. Spring Data JPA (ORM)- using database in our project
5. OAuth for social login - for Google and GitHub login
6. Thymleaf template engine
7. Validation
8. Spring Security - for sending and receiving email
9. PostgreSQL
10. Java email services - for sending and receiving email
11. AWS/Cloudinary SDK - for storing files
12. JavaScript
13. Tailwind CSS
14. Flowbite components
15. Pdf/excel tool for generating reports


Dependency of this project
1. web
2. thymeleaf
3. data jpa
4. validation
5. security
6. outh2 - client
7. modelmapper
8. starter mail
9. lombok
10. devtools
11. mysql connector

