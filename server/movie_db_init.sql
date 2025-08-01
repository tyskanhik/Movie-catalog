-- 1. Создание базы данных и пользователя (если еще не создано)
CREATE DATABASE movie_db;
CREATE USER movie_user WITH PASSWORD 'password';
GRANT ALL PRIVILEGES ON DATABASE movie_db TO movie_user;

-- 2. Подключение к БД
\c movie_db

-- 3. Создание таблиц
CREATE TABLE genres (
    id SERIAL PRIMARY KEY,
    name VARCHAR(50) UNIQUE NOT NULL
);

CREATE TABLE movies (
    id SERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    year INTEGER NOT NULL,
    director VARCHAR(255) NOT NULL,
    plot TEXT NOT NULL,
    poster_url VARCHAR(512),
    rating NUMERIC(3,1) NOT NULL,
    duration VARCHAR(20) NOT NULL,
    age_rating VARCHAR(10) NOT NULL
);

CREATE TABLE movie_genres (
    movie_id INTEGER REFERENCES movies(id) ON DELETE CASCADE,
    genre_id INTEGER REFERENCES genres(id) ON DELETE CASCADE,
    PRIMARY KEY (movie_id, genre_id)
);

-- 4. Создание индексов для ускорения поиска
CREATE INDEX idx_movies_title ON movies(title);
CREATE INDEX idx_movies_year ON movies(year);
CREATE INDEX idx_movies_rating ON movies(rating);

-- 5. Наполнение таблицы жанров
INSERT INTO genres (name) VALUES 
('Криминал'), ('Драма'), ('Триллер'), ('Боевик'),
('Фантастика'), ('Приключения'), ('Фэнтези'), ('Роман'),
('Мультфильм'), ('Мюзикл'), ('Детектив'), ('Комедия');

-- 6. Наполнение таблицы фильмов
INSERT INTO movies (title, year, director, plot, poster_url, rating, duration, age_rating) VALUES
('Крестный отец', 1972, 'Фрэнсис Форд Коппола', 'Дон Корлеоне, глава мафиозного клана, передаёт управление своему сыну...', 'assets/godfather.jpg', 9.2, '2ч 55м', '16+'),
('Побег из Шоушенка', 1994, 'Фрэнк Дарабонт', 'Бухгалтер Энди Дюфрейн обвинён в убийстве собственной жены...', 'assets/Shawshank.jpeg', 9.3, '2ч 22м', '16+'),
('Криминальное чтиво', 1994, 'Квентин Тарантино', 'Истории бандитов пересекаются самым неожиданным образом...', 'assets/PulpFiction.jpg', 8.9, '2ч 34м', '16+'),
('Темный рыцарь', 2008, 'Кристофер Нолан', 'Бэтмен сталкивается с Джокером, хаотичным преступником...', 'assets/DarkKnight.jpg', 9.0, '2ч 32м', '16+'),
('Бойцовский клуб', 1999, 'Дэвид Финчер', 'Страдающий бессонницей офисный работник создает подпольный клуб...', 'assets/FightClub.jpg', 8.8, '2ч 19м', '16+'),
('Матрица', 1999, 'Вачовски', 'Хакер Нео узнает, что его мир - симуляция...', 'assets/Matrix.jpg', 8.7, '2ч 16м', '16+'),
('Форрест Гамп', 1994, 'Роберт Земекис', 'История простого человека, ставшего свидетелем ключевых событий Америки...', 'assets/ForrestGump.jpeg', 8.8, '2ч 22м', '12+'),
('Зеленая миля', 1999, 'Фрэнк Дарабонт', 'История надзирателя и необычного заключенного в тюрьме...', 'assets/GreenMile.jpeg', 9.1, '3ч 09м', '16+'),
('Назад в будущее', 1985, 'Роберт Земекис', 'Подросток случайно отправляется в 1955 год на машине времени...', 'assets/BackToFuture.jpg', 8.5, '1ч 56м', '12+'),
('Король Лев', 1994, 'Роджер Аллерс', 'Молодой львенок Симба познает круговорот жизни...', 'assets/LionKing.jpg', 8.5, '1ч 28м', '6+'),
('Интерстеллар', 2014, 'Кристофер Нолан', 'Группа исследователей совершает путешествие через червоточину...', 'assets/Interstellar.jpeg', 8.6, '2ч 49м', '12+'),
('Властелин колец: Братство кольца', 2001, 'Питер Джексон', 'Фродо Бэггинс отправляется в путь, чтобы уничтожить Кольцо Всевластья...', 'assets/LOTR.jpg', 8.8, '2ч 58м', '12+'),
('Гарри Поттер и философский камень', 2001, 'Крис Коламбус', 'Мальчик-сирота узнает, что он волшебник...', 'assets/HarryPotter.jpg', 7.6, '2ч 32м', '12+'),
('Титаник', 1997, 'Джеймс Кэмерон', 'Роман между аристократкой и художником на борту обреченного корабля...', 'assets/Titanik.jpg', 7.8, '3ч 14м', '12+'),
('Аватар', 2009, 'Джеймс Кэмерон', 'Парализованный морпех становится частью программы на планете Пандора...', 'assets/Avatar.jpeg', 7.8, '2ч 42м', '12+'),
('Достать ножи', 2019, 'Райан Джонсон', 'Детектив расследует загадочное убийство в особняке...', 'assets/KnivesOut.webp', 7.9, '2ч 10м', '16+'),
('Джентльмены', 2019, 'Гай Ричи', 'Криминальная история о британском наркобароне...', 'assets/Gentlemen.jpg', 8.0, '1ч 53м', '16+'),
('Остров проклятых', 2010, 'Мартин Скорсезе', 'Детектив расследует исчезновение пациентки психиатрической клиники...', 'assets/ShutterIsland.jpeg', 8.2, '2ч 18м', '16+');

-- 7. Создание связей между фильмами и жанрами
INSERT INTO movie_genres (movie_id, genre_id) VALUES
-- Крестный отец
(1, 1), (1, 2),
-- Побег из Шоушенка
(2, 2),
-- Криминальное чтиво
(3, 1), (3, 3),
-- Темный рыцарь
(4, 4), (4, 3),
-- Бойцовский клуб
(5, 2), (5, 3),
-- Матрица
(6, 5), (6, 4),
-- Форрест Гамп
(7, 2), (7, 8),
-- Зеленая миля
(8, 2), (8, 7),
-- Назад в будущее
(9, 5), (9, 6),
-- Король Лев
(10, 9), (10, 10),
-- Интерстеллар
(11, 5), (11, 2),
-- Властелин колец
(12, 7), (12, 6),
-- Гарри Поттер
(13, 7), (13, 6),
-- Титаник
(14, 2), (14, 8),
-- Аватар
(15, 5), (15, 4),
-- Достать ножи
(16, 11), (16, 12),
-- Джентльмены
(17, 1), (17, 12),
-- Остров проклятых
(18, 3), (18, 11);

-- 8. Настройка прав пользователя
GRANT SELECT, INSERT, UPDATE, DELETE ON ALL TABLES IN SCHEMA public TO movie_user;
GRANT USAGE, SELECT ON ALL SEQUENCES IN SCHEMA public TO movie_user;

-- 9. Проверочные запросы
-- Все фильмы с жанрами
SELECT m.title, m.year, string_agg(g.name, ', ') AS genres, m.rating
FROM movies m
JOIN movie_genres mg ON m.id = mg.movie_id
JOIN genres g ON mg.genre_id = g.id
GROUP BY m.id, m.title, m.year, m.rating
ORDER BY m.rating DESC;

-- Фильмы определенного жанра (пример для Драмы)
SELECT m.title, m.year, m.rating
FROM movies m
JOIN movie_genres mg ON m.id = mg.movie_id
JOIN genres g ON mg.genre_id = g.id
WHERE g.name = 'Драма'
ORDER BY m.rating DESC;