INSERT INTO USERS (username, first_name, last_name, admin, author)
VALUES ( 'adminUser', 'Admin', 'User', true, true );

INSERT INTO USERS (username, first_name, last_name, admin, author)
VALUES ( 'johnDoe', 'John', 'Doe', false, true );

INSERT INTO USERS (username, first_name, last_name, admin, author)
VALUES ( 'janeDoe', 'Jane', 'Doe', false, false );

INSERT INTO USERS (username, first_name, last_name, admin, author)
VALUES ( 'adamSmith', 'Adam', 'Smith', false, true );

INSERT INTO USERS (username, first_name, last_name, admin, author)
VALUES ( 'SLJ', 'Samuel', 'Jackson', false, false );

INSERT INTO USERS (username, first_name, last_name, admin, author)
VALUES ( 'tLasso', 'Ted', 'Lasso', false, false );

INSERT INTO USERS (username, first_name, last_name, admin, author)
VALUES ( 'KT', 'Kevin', 'Trent', false, true );


INSERT INTO TOPICS (TOPIC_NAME)
VALUES ('Sport');

INSERT INTO TOPICS (TOPIC_NAME)
VALUES ('Culture');

INSERT INTO TOPICS (TOPIC_NAME)
VALUES ('War');

INSERT INTO TOPICS (TOPIC_NAME)
VALUES ('Technologies');


INSERT INTO NEWS (TITLE, CONTENT, LIKES, PUBLISHED_AT, AUTHOR_ID, TOPIC_ID)
VALUES ('Record-Breaking Marathon: Runner Shatters World Record, Sets New Bar for Speed', 'In a stunning display of athleticism, a seasoned athlete defied all expectations by breaking the long-standing world record for the marathon. Clocking an unprecedented time of [1:10:15], the runner left spectators in awe as they crossed the finish line, solidifying their place in sports history. This extraordinary achievement demonstrates the extraordinary potential of human endurance and sets a new standard for future athletes to strive towards.',
        15, CURRENT_TIMESTAMP(), 2, 1);

INSERT INTO NEWS (TITLE, CONTENT, LIKES, PUBLISHED_AT, AUTHOR_ID, TOPIC_ID)
VALUES ('Underdog Triumph: Unranked Team Stuns Competitors, Secures Championship Victory', 'In a remarkable turn of events, an unranked team emerged as the ultimate champions in a fiercely contested tournament. Defying all odds, they outperformed favored opponents and displayed exceptional teamwork and skill. Their journey from underdogs to victors serves as a testament to the unpredictable nature of sports and the power of determination. This stunning upset will be remembered as a remarkable chapter in sports history.',
        5, CURRENT_TIMESTAMP(), 4, 1);

INSERT INTO NEWS (TITLE, CONTENT, LIKES, PUBLISHED_AT, AUTHOR_ID, TOPIC_ID)
VALUES ('Inspiring Comeback: Athlete Overcomes Injury, Returns to Dominance', 'After enduring a devastating injury that threatened their career, an esteemed athlete has made an inspiring comeback, reclaiming their former glory. With unwavering determination and months of rigorous rehabilitation, they defied expectations and returned to the competitive arena stronger than ever. Their remarkable story of resilience and perseverance serves as an inspiration to athletes facing similar challenges, proving that setbacks can be overcome with sheer determination and a fighting spirit.',
        53, CURRENT_TIMESTAMP(), 4, 1);


INSERT INTO NEWS (TITLE, CONTENT, LIKES, PUBLISHED_AT, AUTHOR_ID, TOPIC_ID)
VALUES ('Tenuous Ceasefire Holds: Nations Strive for Peace Amidst Ongoing Conflict', 'As tensions persist in a long-standing conflict, involved nations have reached a tenuous ceasefire agreement in a collective effort to bring an end to hostilities. With hopes of fostering lasting peace, diplomats and negotiators are diligently working towards a comprehensive resolution. While challenges and uncertainties remain, the commitment to dialogue and de-escalation signifies a crucial step forward in finding a peaceful resolution to the conflict.',
        64, CURRENT_TIMESTAMP(), 2, 3);

INSERT INTO NEWS (TITLE, CONTENT, LIKES, PUBLISHED_AT, AUTHOR_ID, TOPIC_ID)
VALUES ('Humanitarian Crisis Unfolds: Civilians Bear the Brunt of Devastating Conflict', 'In the midst of a harrowing conflict, a dire humanitarian crisis continues to unfold, leaving countless innocent civilians trapped in a cycle of violence and suffering. With limited access to basic necessities, including food, water, and healthcare, the plight of these vulnerable populations remains a pressing concern. International aid organizations are working tirelessly to provide much-needed relief, but the urgent need for a comprehensive and sustainable solution to end the conflict and alleviate human suffering is evident.',
        28, CURRENT_TIMESTAMP(), 7, 3);



INSERT INTO NEWS (TITLE, CONTENT, LIKES, PUBLISHED_AT, AUTHOR_ID, TOPIC_ID)
VALUES ('Breakthrough in Quantum Computing: Researchers Achieve Major Milestone in Quantum Supremacy', 'Scientists have achieved a groundbreaking milestone in quantum computing, demonstrating quantum supremacy in a recent experiment. By harnessing the power of quantum mechanics, researchers successfully solved a complex problem that would take traditional computers thousands of years to solve. This achievement paves the way for exponential advancements in fields such as cryptography, optimization, and drug discovery.',
        69, CURRENT_TIMESTAMP(), 2, 4);

INSERT INTO NEWS (TITLE, CONTENT, LIKES, PUBLISHED_AT, AUTHOR_ID, TOPIC_ID)
VALUES ('Revolutionary AI System Enhances Medical Diagnoses, Saving Lives', 'A state-of-the-art artificial intelligence (AI) system has revolutionized medical diagnostics, significantly improving accuracy and saving lives. By analyzing vast amounts of medical data and leveraging machine learning algorithms, this innovative technology can detect early signs of diseases, predict patient outcomes, and assist healthcare professionals in making well-informed treatment decisions. This remarkable advancement marks a significant leap forward in personalized medicine.',
        21, CURRENT_TIMESTAMP(), 2, 4);

INSERT INTO NEWS (TITLE, CONTENT, LIKES, PUBLISHED_AT, AUTHOR_ID, TOPIC_ID)
VALUES ('Next-Generation Robotics Takes Center Stage, Redefining Industries', 'The emergence of next-generation robotics is transforming industries across the globe. From manufacturing and logistics to healthcare and agriculture, advanced robots equipped with machine learning and autonomous capabilities are streamlining processes, boosting productivity, and enhancing safety. With robots working alongside humans, this technological shift promises increased efficiency, cost savings, and exciting possibilities for innovation in various sectors.',
        66, CURRENT_TIMESTAMP(), 4, 4);

INSERT INTO NEWS (TITLE, CONTENT, LIKES, PUBLISHED_AT, AUTHOR_ID, TOPIC_ID)
VALUES ('Advancements in Renewable Energy: Breakthrough in Solar Technology', 'A breakthrough in solar technology brings promising advancements in renewable energy. Scientists have developed a novel solar panel design that maximizes energy conversion efficiency, even in low-light conditions. By integrating innovative materials and advanced engineering techniques, this development holds the potential to significantly enhance the adoption of solar power as a sustainable and clean energy source, reducing reliance on fossil fuels.',
        53, CURRENT_TIMESTAMP(), 7, 4);

INSERT INTO NEWS (TITLE, CONTENT, LIKES, PUBLISHED_AT, AUTHOR_ID, TOPIC_ID)
VALUES ('5G Network Expansion Accelerates: Transforming Connectivity and Communication', 'The global expansion of 5G networks is rapidly reshaping connectivity and communication. With lightning-fast speeds and ultra-low latency, 5G technology enables seamless data transfer, unlocks the potential of Internet of Things (IoT) devices, and supports emerging technologies like autonomous vehicles and remote surgeries. As 5G networks continue to expand, society can expect transformative changes in various sectors, revolutionizing how we live, work, and interact.',
        12, CURRENT_TIMESTAMP(), 7, 4);


INSERT INTO NEWS (TITLE, CONTENT, LIKES, PUBLISHED_AT, AUTHOR_ID, TOPIC_ID)
VALUES ('Celebrating Diversity: Multicultural Festival Showcases Rich Tapestry of Global Traditions', 'A vibrant multicultural festival brought communities together to celebrate diversity and showcase the rich tapestry of global traditions. Attendees experienced a colorful array of music, dance, art, and cuisine from different cultures, fostering cross-cultural understanding and appreciation. This event served as a reminder of the beauty and significance of cultural diversity in creating a harmonious society.',
        2, CURRENT_TIMESTAMP(), 4, 2);


INSERT INTO NEWS (TITLE, CONTENT, LIKES, PUBLISHED_AT, AUTHOR_ID, TOPIC_ID)
VALUES ('Preserving Heritage: Historic Landmark Receives Restoration to Protect Cultural Legacy', 'A cherished historic landmark underwent a meticulous restoration project, preserving its cultural legacy for future generations. Skilled artisans and conservation experts worked tirelessly to revitalize the architectural marvel, ensuring that its unique heritage and artistic significance are safeguarded. This restoration project stands as a testament to the importance of preserving cultural landmarks as a link to our collective past.',
        15, CURRENT_TIMESTAMP(), 4, 2);


INSERT INTO NEWS (TITLE, CONTENT, LIKES, PUBLISHED_AT, AUTHOR_ID, TOPIC_ID)
VALUES ('Artistic Expression Takes Center Stage: Local Gallery Showcases Emerging Talent', 'A local art gallery dedicated its space to showcasing the works of talented emerging artists, providing them with a platform to express their creativity. Through a diverse range of mediums and styles, these artists explored thought-provoking themes and perspectives, captivating audiences and invigorating the local arts scene. This exhibition underscores the vitality of supporting and nurturing artistic talent within the community.',
        37, CURRENT_TIMESTAMP(), 7, 2);


INSERT INTO NEWS (TITLE, CONTENT, LIKES, PUBLISHED_AT, AUTHOR_ID, TOPIC_ID)
VALUES ('Cultural Exchange Initiative Connects Global Communities, Bridging Borders', 'A cultural exchange initiative fostered meaningful connections and bridged borders, bringing together individuals from diverse backgrounds. Through art exhibitions, music performances, and interactive workshops, participants engaged in dialogue and shared their unique cultural heritage. This initiative promoted mutual respect, understanding, and appreciation, highlighting the power of cultural exchange in building bridges across communities.',
        32, CURRENT_TIMESTAMP(), 2, 2);
