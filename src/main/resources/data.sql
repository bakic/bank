INSERT INTO CUSTOMER VALUES (1, 'Mark', 'WILSON', '0766554433', 'mark.wilson@mail.com');
INSERT INTO CUSTOMER VALUES (2, 'John', 'DOE', '0611223344', 'john.doe@mail.com');

INSERT INTO ACCOUNT VALUES(1, 0, TO_DATE('01/07/2018', 'DD/MM/YYYY'), 'Account1', 1);
INSERT INTO ACCOUNT VALUES(2, 0, TO_DATE('02/07/2018', 'DD/MM/YYYY'), 'Account2', 1);

INSERT INTO ACCOUNT VALUES(3, 0, TO_DATE('01/05/2017', 'DD/MM/YYYY'), 'Account1', 2);

INSERT INTO CUSTOMER_ACCOUNTS VALUES (1, 1);
INSERT INTO CUSTOMER_ACCOUNTS VALUES (1, 2);
INSERT INTO CUSTOMER_ACCOUNTS VALUES (2, 3);



