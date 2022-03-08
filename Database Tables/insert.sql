-----------------Location-----
INSERT INTO location VALUES
(
'10001i',
'Islamabad',
'ICT',
'Pakistan',
44000,
TO_DATE('2000','YYYY')
);
INSERT INTO location VALUES
(
'10002k',
'Karachi',
'Sindh',
'Pakistan',
24700,
TO_DATE('2000','YYYY')
);
INSERT INTO location VALUES
(
'10003l',
'Lahore',
'Punjab',
'Pakistan',
51000,
TO_DATE('2000','YYYY')
);
INSERT INTO location VALUES
(
'10004c',
'Chiniot',
'Punjab',
'Pakistan',
38070,
TO_DATE('2015','YYYY')
);
INSERT INTO location VALUES
(
'10005p',
'Peshawar',
'KPK',
'Pakistan',
25120,
TO_DATE('2001','YYYY')
);
-------------------------------Department---------------------
INSERT INTO department VALUES
(
'200001',
'CS Academic Office',
'10001i'
);
INSERT INTO department VALUES
(
'200002',
'Central Academic Office',
'10001i'
);
INSERT INTO department VALUES
(
'200003',
'Student Affairs Office',
'10001i'
);
INSERT INTO department VALUES
(
'200004',
'Library',
'10001i'
);
INSERT INTO department VALUES
(
'200005',
'IT & Network Office',
'10001i'
);
INSERT INTO department VALUES
(
'200006',
'Disciplinary Committee',
'10001i'
);
INSERT INTO department VALUES
(
'200007',
'Sports Office',
'10001i'
);
INSERT INTO department VALUES
(
'200008',
'Transport',
'10001i'
);
INSERT INTO department VALUES
(
'200009',
'Cafeteria',
'10001i'
);
INSERT INTO department VALUES
(
'200010',
'Boarding House',
'10001i'
);
INSERT INTO department VALUES
(
'200011',
'Engineering Labs',
'10001i'
);
INSERT INTO department VALUES
(
'200012',
'FYP Committee',
'10001i'
);
INSERT INTO department VALUES
(
'200013',
'Head Office',
'10001i'
);
INSERT INTO department VALUES
(
'200014',
'Accounts',
'10001i'
);
INSERT INTO department VALUES
(
'200015',
'One Stop',
'10001i'
);
INSERT INTO department VALUES
(
'200016',
'HR',
'10001i'
);
----------------------------------------employee_n-----------------------
INSERT INTO employee_n VALUES
(
'400001',
'Amir',
'Rehman',
'5',
'10',
'E11/4',
'Islamabad',
'ICT',
44000,
TO_DATE('1989-06-01','YYYY-MM-DD'),
'3630256798333',
3008765897,
'amir.rehman@nu.edu.pk',
'Academic Officer',
TO_DATE('2015-01-05','YYYY-MM-DD'),
'200001',
'@admin1234'
);
INSERT INTO employee_n VALUES
(
'400002',
'Abdul',
'Hameed',
'11',
'3',
'F7/3',
'Islamabad',
'ICT',
44000,
TO_DATE('1989-06-02','YYYY-MM-DD'),
'3630256798334',
3008765898,
'abdul.hameed@nu.edu.pk',
'Deputy Manager Academics',
TO_DATE('2015-03-15','YYYY-MM-DD'),
'200002',
'@employee12345'
);
INSERT INTO employee_n VALUES
(
'400003',
'Aman Ullah',
'Khan',
'3',
'8',
'Bahria Town',
'Rawalpindi',
'ICT',
3250,
TO_DATE('1989-06-03','YYYY-MM-DD'),
'3630256798335',
3008765899,
'aman.ullah@nu.edu.pk',
'Manager (Services)',
TO_DATE('2015-07-27','YYYY-MM-DD'),
'200003',
'@1234admin'
);
INSERT INTO employee_n VALUES
(
'400004',
'Qammer',
'Naveed',
'33',
'19',
'F10/2',
'Islamabad',
'ICT',
44000,
TO_DATE('1989-06-04','YYYY-MM-DD'),
'3630256798336',
3008765900,
'qammer.naveed@nu.edu.pk',
'Cheif Librarian',
TO_DATE('2019-01-18','YYYY-MM-DD'),
'200004',
'@12345'
);
INSERT INTO employee_n VALUES
(
'400005',
'Haroon',
'Kareem',
'243',
'7',
'F11/4',
'Islamabad',
'ICT',
44000,
TO_DATE('1989-06-05','YYYY-MM-DD'),
'3630256798337',
3008765901,
'haroon.kareem@nu.edu.pk',
'Manager IT',
TO_DATE('2010-07-22','YYYY-MM-DD'),
'200005',
'@1234Employee'
);
INSERT INTO employee_n VALUES
(
'400006',
'Muhammad',
'Tufail',
'65',
'13',
'Ghouri Town',
'Rawalpindi',
'ICT',
3250,
TO_DATE('1989-06-06','YYYY-MM-DD'),
'3630256798338',
3008765902,
'muhammad.tufail@nu.edu.pk',
'Disciplinary Manager',
TO_DATE('2018-03-29','YYYY-MM-DD'),
'200006',
'@pakistan1947'
);
INSERT INTO employee_n VALUES
(
'400007',
'Aoun',
'Abbas',
'18',
'15',
'DHA Phase3',
'Rawalpindi',
'ICT',
3250,
TO_DATE('1989-06-07','YYYY-MM-DD'),
'3630256798339',
3008765903,
'aoun.abbas@nu.edu.pk',
'Sports Officier',
TO_DATE('2015-06-18','YYYY-MM-DD'),
'200007',
'@pakistan 14'
);
INSERT INTO employee_n VALUES
(
'400008',
'Adnan',
'Shafqat',
'26',
'13',
'Pakistan Town',
'Islamabad',
'ICT',
44000,
TO_DATE('1989-06-08','YYYY-MM-DD'),
'3630256798340',
3008765904,
'adnan.shafqat@nu.edu.pk',
'Transpot Manager',
TO_DATE('2009-07-04','YYYY-MM-DD'),
'200008',
'@loginID1234'
);
INSERT INTO employee_n VALUES
(
'400009',
'Moiz',
'Gujjar',
'5',
'3',
'ICHS',
'Islamabad',
'ICT',
44000,
TO_DATE('1989-06-09','YYYY-MM-DD'),
'3630256798341',
3008765905,
'moizgujjar@nu.edu.pk',
'Cafe Owner',
TO_DATE('2013-05-12','YYYY-MM-DD'),
'200009',
'@Gujjar1234'
);
INSERT INTO employee_n VALUES
(
'400010',
'Adil Bin',
'Zahid',
'21',
'07',
'AGOCHS Phase 2',
'Islamabad',
'ICT',
44000,
TO_DATE('1989-06-10','YYYY-MM-DD'),
'3630256798342',
3008765906,
'adil.zahid@nu.edu.pk',
'Boarding Officer',
TO_DATE('2019-02-16','YYYY-MM-DD'),
'200010',
'@zahidadil5896'
);
INSERT INTO employee_n VALUES
(
'400011',
'Muhammad',
'Habib Ullah',
'14',
'5',
'Al Hamra Avenue',
'Islamabad',
'ICT',
44000,
TO_DATE('1989-06-11','YYYY-MM-DD'),
'3630256798343',
3008765907,
'habib.khan@nu.edu.pk',
'Deputy Manager IT',
TO_DATE('2020-05-25','YYYY-MM-DD'),
'200011',
'@Qualityversion7586'
);
INSERT INTO employee_n VALUES
(
'400012',
'Shoaib',
'Mehboob',
'9',
'1',
'G10/3',
'Islamabad',
'ICT',
44000,
TO_DATE('1989-06-12','YYYY-MM-DD'),
'3630256798344',
3008765908,
'shohaib.mehboob@nu.edu.pk',
'Committe Head',
TO_DATE('2016-02-26','YYYY-MM-DD'),
'200012',
'@Qualityteam7586'
);
INSERT INTO employee_n VALUES
(
'400013',
'Aftab',
'Ahmad',
'7',
'33',
'E11/3',
'Islamabad',
'ICT',
44000,
TO_DATE('1989-06-13','YYYY-MM-DD'),
'3630256798345',
3008765909,
'aftab.maroof@nu.edu.pk',
'Reactor',
TO_DATE('2005-03-08','YYYY-MM-DD'),
'200013',
'@fastnuces1234'
);
INSERT INTO employee_n VALUES
(
'400014',
'Irshad',
'Ahmad',
'23',
'8',
'F17',
'Islamabad',
'ICT',
44000,
TO_DATE('1989-06-14','YYYY-MM-DD'),
'3630256798346',
3008765910,
'irshad.muhammad@nu.edu.pk',
'Account Officer',
TO_DATE('2017-03-27','YYYY-MM-DD'),
'200014',
'@nucesfastisb1234'
);
INSERT INTO employee_n VALUES
(
'400015',
'Sumaiiyah',
'Iqbal',
'32',
'2',
'Top City',
'Islamabad',
'ICT',
44000,
TO_DATE('1989-06-15','YYYY-MM-DD'),
'3630256798347',
3008765911,
'sumaiyah.iqbal@nu.edu.pk',
'Assistant One Stop',
TO_DATE('2018-04-30','YYYY-MM-DD'),
'200015',
'@1947pakistan'
);
INSERT INTO employee_n VALUES
(
'400016',
'Hina',
'Amjad',
'18',
'13',
'Park View City',
'Islamabad',
'ICT',
44000,
TO_DATE('1989-06-16','YYYY-MM-DD'),
'3630256798348',
3008765912,
'hina.amjad@nu.edu.pk',
'Manager HR IT',
TO_DATE('2021-01-04','YYYY-MM-DD'),
'200016',
'@19pakistan47'
);
------------------------------------Applicant------------------
INSERT INTO applicant VALUES
(
'900001',
'Talat',
'Naeem',
3510219187448,
3008756987,
'talat.naeem@nu.edu.pk',
'@12345*'
);
INSERT INTO applicant VALUES
(
'900002',
'Haris',
'khan',
3510312157178,
3006954089,
'haris.khan@nu.edu.pk',
'@haris4199722*'
);
INSERT INTO applicant VALUES
(
'900003',
'khilat',
'Naqvi',
3510443568781,
3000007428,
'khilat.naqvi@nu.edu.pk',
'@naqvikhilat7428*'
);
INSERT INTO applicant VALUES
(
'900004',
'arshad',
'Naeem',
3510211158998,
3001754988,
'arshad.naeem@nu.edu.pk',
'@greatpakistan123*'
);
INSERT INTO applicant VALUES
(
'900005',
'arbab',
'khan',
3510530229922,
3001850922,
'arbab.khan@nu.edu.pk',
'@12345*'
);
INSERT INTO applicant VALUES
(
'900006',
'uzair',
'ibrar',
3510211187771,
3008136995,
'uzair.ibrar@nu.edu.pk',
'@bitsol1905*'
);
INSERT INTO applicant VALUES
(
'900007',
'haseeb',
'rehmat',
3510215124145,
3003152907,
'haseeb.rehmat@nu.edu.pk',
'@goldminer123*'
);
INSERT INTO applicant VALUES
(
'900008',
'nouman',
'khan',
3510129389140,
3008156911,
'nouman.khan@nu.edu.pk',
'@greatpakistan111*'
);
INSERT INTO applicant VALUES
(
'900009',
'osama',
'rafique',
3510211127448,
3008336977,
'osama.rafique@nu.edu.pk',
'@bestosama1234*'
);
INSERT INTO applicant VALUES
(
'900010',
'aqib',
'ajmal',
3510555287798,
3008959997,
'aqib.ajmal@nu.edu.pk',
'@notaqib1234*'
);
---------------------------------------------Document Type-------------
INSERT INTO document_type VALUES
(
'Course Replacement Form',
0,
'NA'
);
INSERT INTO document_type VALUES
(
'Clearance Form',
1,
'NA'
);
INSERT INTO document_type VALUES
(
'Course Withdraw Form',
1,
'NA'
);
INSERT INTO document_type VALUES
(
'Grade Change Form',
1,
'NA'
);
INSERT INTO document_type VALUES
(
'Semester Freeze Form',
1,
'NA'
);
INSERT INTO document_type VALUES
(
'Event Request Form',
0,
'NA'
);
----------------------------------------------Document--------------------
INSERT INTO document VALUES
(
'800001',
'Course Replacement Form',
TO_DATE('2021-06-26','YYYY-MM-DD'),
TO_DATE('2021-06-26','YYYY-MM-DD'),
1,
1,
'400001',
'900001'
);
INSERT INTO document VALUES
(
'800002',
'Clearance Form',
TO_DATE('2021-06-26','YYYY-MM-DD'),
TO_DATE('2021-06-26','YYYY-MM-DD'),
2,
2,
'400001',
'900001'
);
INSERT INTO document VALUES
(
'800003',
'Course Withdraw Form',
TO_DATE('2021-06-26','YYYY-MM-DD'),
TO_DATE('2021-06-26','YYYY-MM-DD'),
3,
1,
'400001',
'900001'
);
INSERT INTO document VALUES
(
'800004',
'Grade Change Form',
TO_DATE('2021-06-26','YYYY-MM-DD'),
TO_DATE('2021-06-26','YYYY-MM-DD'),
4,
1,
'400001',
'900001'
);
INSERT INTO document VALUES
(
'800005',
'Semester Freeze Form',
TO_DATE('2021-06-26','YYYY-MM-DD'),
TO_DATE('2021-06-26','YYYY-MM-DD'),
5,
1,
'400001',
'900001'
);
INSERT INTO document VALUES
(
'800006',
'Event Request Form',
TO_DATE('2021-06-26','YYYY-MM-DD'),
TO_DATE('2021-06-26','YYYY-MM-DD'),
6,
1,
'400001',
'900001'
);
---------------------------------------Document Status--------------------------
INSERT INTO document_status VALUES
(
1,
'800002',
TO_DATE('2021-10-14','YYYY-MM-DD'),
'400001',
'None'
);
INSERT INTO document_status VALUES
(
2,
'800002',
TO_DATE('2021-10-15','YYYY-MM-DD'),
'400002',
'None'
);
INSERT INTO document_status VALUES
(
3,
'800002',
TO_DATE('2021-10-16','YYYY-MM-DD'),
'400003',
'None'
);
INSERT INTO document_status VALUES
(
4,
'800002',
TO_DATE('2021-10-17','YYYY-MM-DD'),
'400004',
'None'
);
INSERT INTO document_status VALUES
(
5,
'800002',
TO_DATE('2021-10-18','YYYY-MM-DD'),
'400005',
'None'
);
INSERT INTO document_status VALUES
(
6,
'800002',
TO_DATE('2021-10-19','YYYY-MM-DD'),
'400006',
'None'
);
INSERT INTO document_status VALUES
(
7,
'800002',
TO_DATE('2021-10-20','YYYY-MM-DD'),
'400007',
'None'
);
INSERT INTO document_status VALUES
(
8,
'800002',
TO_DATE('2021-10-21','YYYY-MM-DD'),
'400008',
'None'
);
INSERT INTO document_status VALUES
(
9,
'800002',
TO_DATE('2021-10-22','YYYY-MM-DD'),
'400009',
'None'
);
INSERT INTO document_status VALUES
(
10,
'800002',
TO_DATE('2021-10-23','YYYY-MM-DD'),
'400010',
'None'
);
INSERT INTO document_status VALUES
(
11,
'800002',
TO_DATE('2021-10-24','YYYY-MM-DD'),
'400011',
'None'
);
INSERT INTO document_status VALUES
(
12,
'800002',
TO_DATE('2021-10-25','YYYY-MM-DD'),
'400012',
'None'
);
INSERT INTO document_status VALUES
(
13,
'800002',
TO_DATE('2021-10-26','YYYY-MM-DD'),
'400013',
'None'
);
INSERT INTO document_status VALUES
(
14,
'800002',
TO_DATE('2021-10-27','YYYY-MM-DD'),
'400014',
'None'
);
INSERT INTO document_status VALUES
(
15,
'800002',
TO_DATE('2021-10-28','YYYY-MM-DD'),
'400015',
'None'
);
INSERT INTO document_status VALUES
(
16,
'800002',
TO_DATE('2021-10-29','YYYY-MM-DD'),
'400016',
'None'
);

INSERT INTO document_route VALUES
(
1,
'Clearance Form',
'200001'
);
INSERT INTO document_route VALUES
(
2,
'Clearance Form',
'200002'
);
INSERT INTO document_route VALUES
(
3,
'Clearance Form',
'200003'
);
INSERT INTO document_route VALUES
(
4,
'Clearance Form',
'200004'
);
INSERT INTO document_route VALUES
(
5,
'Clearance Form',
'200005'
);
INSERT INTO document_route VALUES
(
6,
'Clearance Form',
'200006'
);
INSERT INTO document_route VALUES
(
7,
'Clearance Form',
'200007'
);
INSERT INTO document_route VALUES
(
8,
'Clearance Form',
'200008'
);
INSERT INTO document_route VALUES
(
9,
'Clearance Form',
'200009'
);
INSERT INTO document_route VALUES
(
10,
'Clearance Form',
'200010'
);
INSERT INTO document_route VALUES
(
11,
'Clearance Form',
'200011'
);
INSERT INTO document_route VALUES
(
12,
'Clearance Form',
'200012'
);
INSERT INTO document_route VALUES
(
13,
'Clearance Form',
'200013'
);
INSERT INTO document_route VALUES
(
14,
'Clearance Form',
'200014'
);
INSERT INTO document_route VALUES
(
15,
'Clearance Form',
'200015'
);
INSERT INTO document_route VALUES
(
16,
'Clearance Form',
'200016'
);


COMMIT;