INSERT INTO ROLES(ROLE_NAME)
VALUES('ROLE_ADMIN');

INSERT INTO ROLES(ROLE_NAME)
VALUES('ROLE_AUTHOR');

INSERT INTO ROLES(ROLE_NAME)
VALUES('ROLE_USER');


INSERT INTO USERS (username, password, first_name, last_name)
VALUES ( 'adminUser','$2a$12$E7z3QBrzBSXSFPh7kSMrV.L4RwaOwrN2GtUyBfapH9VtpKoPzeE0a', 'Admin', 'User');

INSERT INTO USERS (username, password, first_name, last_name)
VALUES ( 'johnDoe','$2a$12$/reLQ9FKAX2spmhAJx1WduqSsJI4VhF0sldPKBHSTFZOB80bI/J22', 'John', 'Doe');

INSERT INTO USERS (username, password, first_name, last_name)
VALUES ( 'janeDoe','$2a$12$QX.6YXoENGtx5JsK5JnheOhKWNqK6oDv3HcSzALivhlmK9eKZU/hG', 'Jane', 'Doe');

INSERT INTO USERS (username, password, first_name, last_name)
VALUES ( 'adamSmith','$2a$12$R3SZObi/TetERzXMqJsbPOe3ns7I0.RFUqhbjio0oyGq2.bNO0nCO', 'Adam', 'Smith');

INSERT INTO USERS (username, password, first_name, last_name)
VALUES ( 'SLJ','$2a$12$/YgBPtH1rz.UrcKQxIjMgudEuw7Rf9z7zu1LnwaxB3.096ba0P9DC', 'Samuel', 'Jackson');

INSERT INTO USERS (username, password, first_name, last_name)
VALUES ( 'tLasso','$2a$12$BE1kFrdT5h5nMNvgOMg9jOEjI50k15O0APoCHUXTgtPu9.KFDS3Nq', 'Ted', 'Lasso');

INSERT INTO USERS (username, password, first_name, last_name)
VALUES ( 'KT','$2a$12$y33OT2XNGtD9WGgfJGg00.s.8nPMQ7wDw7e0cvXl7hQISrdM3raui', 'Kevin', 'Trent');


INSERT INTO USERS_ROLES(USER_ID, ROLE_ID)
VALUES (1,1);

INSERT INTO USERS_ROLES(USER_ID, ROLE_ID)
VALUES (1,2);

INSERT INTO USERS_ROLES(USER_ID, ROLE_ID)
VALUES (1,3);

INSERT INTO USERS_ROLES(USER_ID, ROLE_ID)
VALUES (2,2);

INSERT INTO USERS_ROLES(USER_ID, ROLE_ID)
VALUES (2,3);

INSERT INTO USERS_ROLES(USER_ID, ROLE_ID)
VALUES (3,3);

INSERT INTO USERS_ROLES(USER_ID, ROLE_ID)
VALUES (4,2);

INSERT INTO USERS_ROLES(USER_ID, ROLE_ID)
VALUES (4,3);

INSERT INTO USERS_ROLES(USER_ID, ROLE_ID)
VALUES (5,3);

INSERT INTO USERS_ROLES(USER_ID, ROLE_ID)
VALUES (6,3);

INSERT INTO USERS_ROLES(USER_ID, ROLE_ID)
VALUES (7,2);

INSERT INTO USERS_ROLES(USER_ID, ROLE_ID)
VALUES (7,3);


INSERT INTO TOPICS (TOPIC_NAME)
VALUES ('Sport');

INSERT INTO TOPICS (TOPIC_NAME)
VALUES ('Culture');

INSERT INTO TOPICS (TOPIC_NAME)
VALUES ('War');

INSERT INTO TOPICS (TOPIC_NAME)
VALUES ('Technologies');


INSERT INTO NEWS (TITLE, CONTENT, LIKES, PUBLISHED_AT, AUTHOR_ID, TOPIC_ID)
VALUES ('Record-Breaking Marathon: Runner Shatters World Record, Sets New Bar for Speed', 'In a stunning display of athleticism, a seasoned athlete defied all expectations by breaking the long-standing world record for the marathon.',
        15, CURRENT_TIMESTAMP(), 2, 1);

INSERT INTO NEWS (TITLE, CONTENT, LIKES, PUBLISHED_AT, AUTHOR_ID, TOPIC_ID)
VALUES ('Underdog Triumph: Unranked Team Stuns Competitors, Secures Championship Victory', 'In a remarkable turn of events, an unranked team emerged as the ultimate champions in a fiercely contested tournament.',
        5, CURRENT_TIMESTAMP(), 4, 1);

INSERT INTO NEWS (TITLE, CONTENT, LIKES, PUBLISHED_AT, AUTHOR_ID, TOPIC_ID)
VALUES ('Inspiring Comeback: Athlete Overcomes Injury, Returns to Dominance', 'After enduring a devastating injury that threatened their career, an esteemed athlete has made an inspiring comeback, reclaiming their former glory.',
        53, CURRENT_TIMESTAMP(), 4, 1);


INSERT INTO NEWS (TITLE, CONTENT, LIKES, PUBLISHED_AT, AUTHOR_ID, TOPIC_ID)
VALUES ('Tenuous Ceasefire Holds: Nations Strive for Peace Amidst Ongoing Conflict', 'As tensions persist in a long-standing conflict, involved nations have reached a tenuous ceasefire agreement in a collective effort to bring an end to hostilities. With hopes of fostering lasting peace',
        64, CURRENT_TIMESTAMP(), 2, 3);

INSERT INTO NEWS (TITLE, CONTENT, LIKES, PUBLISHED_AT, AUTHOR_ID, TOPIC_ID)
VALUES ('Humanitarian Crisis Unfolds: Civilians Bear the Brunt of Devastating Conflict', 'In the midst of a harrowing conflict, a dire humanitarian crisis continues to unfold, leaving countless innocent civilians trapped in a cycle for a human suffering is evident.',
        28, CURRENT_TIMESTAMP(), 7, 3);



INSERT INTO NEWS (TITLE, CONTENT, LIKES, PUBLISHED_AT, AUTHOR_ID, TOPIC_ID)
VALUES ('Breakthrough in Quantum Computing: Researchers Achieve Major Milestone in Quantum Supremacy', 'Scientists have achieved a groundbreaking milestone in quantum computing, demonstrating quantum supremacy in a recent experiment.',
        69, CURRENT_TIMESTAMP(), 2, 4);

INSERT INTO NEWS (TITLE, CONTENT, LIKES, PUBLISHED_AT, AUTHOR_ID, TOPIC_ID)
VALUES ('Revolutionary AI System Enhances Medical Diagnoses, Saving Lives', 'A state-of-the-art artificial intelligence (AI) system has revolutionized medical diagnostics, significantly improving accuracy and saving lives.',
        21, CURRENT_TIMESTAMP(), 2, 4);

INSERT INTO NEWS (TITLE, CONTENT, LIKES, PUBLISHED_AT, AUTHOR_ID, TOPIC_ID)
VALUES ('Next-Generation Robotics Takes Center Stage, Redefining Industries', 'The emergence of next-generation robotics is transforming industries across the globe. From manufacturing and logistics to healthcare and agriculture, advanced robots equipped with ',
        66, CURRENT_TIMESTAMP(), 4, 4);

INSERT INTO NEWS (TITLE, CONTENT, LIKES, PUBLISHED_AT, AUTHOR_ID, TOPIC_ID)
VALUES ('Advancements in Renewable Energy: Breakthrough in Solar Technology', 'A breakthrough in solar technology brings promising advancements in renewable energy. Scientists have developed a novel solar panel design that maximizes energy conversion efficiency, even in low-light conditions.',
        53, CURRENT_TIMESTAMP(), 7, 4);

INSERT INTO NEWS (TITLE, CONTENT, LIKES, PUBLISHED_AT, AUTHOR_ID, TOPIC_ID)
VALUES ('5G Network Expansion Accelerates: Transforming Connectivity and Communication', 'The global expansion of 5G networks is rapidly reshaping connectivity and communication. With lightning-fast speeds and ultra-low latency, 5G technology enables seamless data transfer',
        12, CURRENT_TIMESTAMP(), 7, 4);


INSERT INTO NEWS (TITLE, CONTENT, LIKES, PUBLISHED_AT, AUTHOR_ID, TOPIC_ID)
VALUES ('Celebrating Diversity: Multicultural Festival Showcases Rich Tapestry of Global Traditions', 'A vibrant multicultural festival brought communities together to celebrate diversity and showcase the rich tapestry of global traditions. Attendees experienced a colorful array of music,',
        2, CURRENT_TIMESTAMP(), 4, 2);


INSERT INTO NEWS (TITLE, CONTENT, LIKES, PUBLISHED_AT, AUTHOR_ID, TOPIC_ID)
VALUES ('Preserving Heritage: Historic Landmark Receives Restoration to Protect Cultural Legacy', 'A cherished historic landmark underwent a meticulous restoration project, preserving its cultural legacy for future generations. Skilled artisans and conservation experts worked tirelessly to',
        15, CURRENT_TIMESTAMP(), 4, 2);


INSERT INTO NEWS (TITLE, CONTENT, LIKES, PUBLISHED_AT, AUTHOR_ID, TOPIC_ID)
VALUES ('Artistic Expression Takes Center Stage: Local Gallery Showcases Emerging Talent', 'A local art gallery dedicated its space to showcasing the works of talented emerging artists, providing them with a platform to express their creativity. Through a diverse range of mediums and styles.',
        37, CURRENT_TIMESTAMP(), 7, 2);


INSERT INTO NEWS (TITLE, CONTENT, LIKES, PUBLISHED_AT, AUTHOR_ID, TOPIC_ID)
VALUES ('Cultural Exchange Initiative Connects Global Communities, Bridging Borders', 'A cultural exchange initiative fostered meaningful connections and bridged borders, bringing together individuals from diverse backgrounds. Through art exhibitions, music performances, and interactive workshops.',
        32, CURRENT_TIMESTAMP(), 2, 2);
