# Clinic Management System
## Table of contents
<!--ts-->
- [Clinic Management System](#clinic-management-system)
  - [Table of contents](#table-of-contents)
  - [Features:](#features)
      - [V-1.1 Update Notes](#v-11-update-notes)
        - [Notice](#notice)
  - [Software Architecture](#software-architecture)
  - [run](#run)
  - [Website display pictures](#website-display-pictures)
    - [Partial display interface of patients and doctors](#partial-display-interface-of-patients-and-doctors)
    - [Administrator interface](#administrator-interface)
    - [v1.1 update interface screenshot](#v11-update-interface-screenshot)

<!-- Created by https://github.com/ekalinin/github-markdown-toc -->
<!-- Added by: runner, at: Tue Sep 27 00:24:25 UTC 2022 -->

<!--te-->
## Features:
The functions of the clinic management system include: patient reservation, drug purchase, order viewing, order management, etc., the doctor handles the appointment, dispensing, appointment management, order processing, etc., and the administrator manages all basic information

#### V-1.1 Update Notes
Modify the system login permission verification, add role medical technicians on the doctor side, medical technicians can write reports for patients, etc., general physicians can view the patient's currently bound medical technical reports and historical medical technical reports, etc., and add medical technical appointments and history on the user side Medical technology appointment viewing, downloading medical technology reports, etc., administrators handle medical technology, medical technology order processing, etc.

##### Notice
This system is completely developed by myself, and is now released on github as a technical exchange. It is strictly forbidden to rent or sell this system to the public. If I violate the rules, I will pursue civil liability in accordance with the law.

## Software Architecture
Backend: The backend uses Spring boot to build the environment, and redis needs to be enabled in the backend
Front-end: use vue, element plus, axios, etc.
Front-end and back-end separation projects

## run
1. Download front-end and back-end files
2. The npm i --legacy-peer-deps command is required before the front end runs
3. Use redis and mysql in the backend, modify the parameter file in application.yml, modify the mysql address and password

## Website display pictures
### Partial display interface of patients and doctors
patient login
![image](https://user-images.githubusercontent.com/87918586/188300980-dc36acf1-eefd-4ee1-b3ba-74c380cac86a.png)

doctor login
![image](https://user-images.githubusercontent.com/87918586/188300988-04e9136e-6a05-44c4-87e9-3d8818699a45.png)

administrator login
![image](https://user-images.githubusercontent.com/87918586/188300993-fd23c01a-f05f-4def-8d4d-de3b635e17fd.png)

Patient Homepage
![image](https://user-images.githubusercontent.com/87918586/188301018-cfbd2d16-9a72-4439-8054-1375bdbcd0e2.png)

Doctor Homepage
![image](https://user-images.githubusercontent.com/87918586/188301031-fbde4d26-7f09-4afd-b75f-77d7c7519fcc.png)

Admin Home
![image](https://user-images.githubusercontent.com/87918586/188301043-1a0d171f-6c0a-482f-9e9c-f31e654cc6c1.png)

patient appointment
![image](https://user-images.githubusercontent.com/87918586/188301062-bb411243-2125-4e49-b066-4f3db45dd296.png)

Pay by appointment
![image](https://user-images.githubusercontent.com/87918586/188301095-2dd87140-0ddd-486d-aee2-524138ebbc78.png)

Doctor handles appointments
![image](https://user-images.githubusercontent.com/87918586/188301111-d1c35927-cf76-4ff1-b124-be05d9aa9039.png)
![image](https://user-images.githubusercontent.com/87918586/188301112-81230a30-d321-403b-8bb3-5c1a6c7884a1.png)

Patient pays order
![image](https://user-images.githubusercontent.com/87918586/188301129-1500a969-26ac-41a6-8acd-5e2b30578a23.png)
![image](https://user-images.githubusercontent.com/87918586/188301139-ca630642-a0ee-4cb5-847b-28b15b7dc648.png)
![image](https://user-images.githubusercontent.com/87918586/188301148-781e9b2e-31a2-41ec-b12d-e5f1ed706999.png)
![image](https://user-images.githubusercontent.com/87918586/188301168-84fa162a-96a3-42b7-9b6f-cb290fecd07c.png)

Doctor handles dispensing
![image](https://user-images.githubusercontent.com/87918586/188301180-86a35ea6-e086-47d0-b040-1b9dee2cc743.png)

patient order review
![image](https://user-images.githubusercontent.com/87918586/188301218-94a40faf-25f7-4cd1-83ff-6d5b4b3eeab5.png)

Doctor Appointment View
![image](https://user-images.githubusercontent.com/87918586/188301245-c053a398-76c2-4a69-8212-2baf65cadbce.png)
Modification of doctor's personal information
![image](https://user-images.githubusercontent.com/87918586/188301260-fa6acddc-3e4d-479f-8213-97757380bee7.png)

### Administrator interface
Too many interfaces only show some functions
View and modify doctor information
![image](https://user-images.githubusercontent.com/87918586/188301291-062ac44f-4527-4d8f-b491-2e3018b69cae.png)
Export and import of doctor information
![image](https://user-images.githubusercontent.com/87918586/188301309-1b9f9d0b-b1f8-43cc-9198-ff9bda870f04.png)
![image](https://user-images.githubusercontent.com/87918586/188301318-c9b386ba-867f-4ad2-b0cf-370a1113f01c.png)
Advertising management
![image](https://user-images.githubusercontent.com/87918586/188301330-e2f72b6c-9948-44b9-904c-757ae4b5188c.png)
data recovery
![image](https://user-images.githubusercontent.com/87918586/188301344-347d03a1-5dde-41a5-a9f4-04a942832df2.png)

### v1.1 update interface screenshot
Physician Medical Tech Role Home Page
![image](https://user-images.githubusercontent.com/87918586/192399128-ec27ebab-65bb-41bb-bfc5-ff06fc448b7d.png)

General Physician Character Homepage
![image](https://user-images.githubusercontent.com/87918586/192399195-d010861a-02b2-4822-b7b1-79df681fde22.png)

Patient appointment medical technology
![image](https://user-images.githubusercontent.com/87918586/192399258-ac94578c-82a3-4a25-87f9-4d58bb70bb5b.png)
![image](https://user-images.githubusercontent.com/87918586/192399276-76288965-8c78-40d8-afe3-e482062a8d3b.png)
![image](https://user-images.githubusercontent.com/87918586/192399321-a4c803eb-ad67-4613-b1ff-598a240a568b.png)

General practitioners can view patient history reports
![image](https://user-images.githubusercontent.com/87918586/192399419-e87a9679-404b-4918-ae20-a661dc891d89.png)

Medical technician fills out report for patient
![image](https://user-images.githubusercontent.com/87918586/192399782-a41594f3-9057-418a-ad61-53f007a33845.png)

Patients view and download reports
![image](https://user-images.githubusercontent.com/87918586/192399846-4a34ea24-5c86-4d38-b5b5-903cffbe97f5.png)

Admin Home
![image](https://user-images.githubusercontent.com/87918586/192399889-50deb03f-2264-4b54-a6c2-3476080dff89.png)
Some pages of the administrator are too repetitive and are no longer displayed