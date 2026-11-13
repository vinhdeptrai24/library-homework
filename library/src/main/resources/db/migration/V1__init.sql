-- ===== Tạo bảng =====

CREATE TABLE books
(
    id               BIGSERIAL PRIMARY KEY,
    isbn             VARCHAR(20)  NOT NULL UNIQUE,
    title            VARCHAR(255) NOT NULL,
    total_copies     INT          NOT NULL CHECK (total_copies >= 1),
    available_copies INT          NOT NULL CHECK (available_copies >= 0),
    CHECK (available_copies <= total_copies)
);

CREATE TABLE members
(
    id    BIGSERIAL PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    name  VARCHAR(255) NOT NULL
);

CREATE TABLE loans
(
    id          BIGSERIAL PRIMARY KEY,
    book_id     BIGINT NOT NULL REFERENCES books (id),
    member_id   BIGINT NOT NULL REFERENCES members (id),
    borrowed_at DATE   NOT NULL,
    due_date    DATE   NOT NULL,
    returned_at DATE -- null = đang mượn
);

-- Một member chỉ có tối đa 1 lượt mượn chưa trả cho mỗi cuốn
CREATE UNIQUE INDEX uq_active_loan
    ON loans (member_id, book_id) WHERE returned_at IS NULL;

-- ===== Seed =====

INSERT INTO members (email, name)
VALUES ('an@example.com', 'Nguyễn Văn An'),
       ('binh@example.com', 'Trần Thị Bình'),
       ('cuong@example.com', 'Lê Văn Cường');

INSERT INTO books (isbn, title, total_copies, available_copies)
VALUES ('978-0132350884', 'Clean Code', 3, 2),
       ('978-0134685991', 'Effective Java', 2, 1),
       ('978-6041000001', 'Dế Mèn phiêu lưu ký', 1, 1),
       ('978-0596009205', 'Head First Java', 5, 5),
       ('978-0201633610', 'Design Patterns', 2, 2);

-- Lượt mượn quá hạn: An mượn Clean Code, trễ 6 ngày (phí = 30.000đ)
INSERT INTO loans (book_id, member_id, borrowed_at, due_date, returned_at)
VALUES (1, 1, CURRENT_DATE - 20, CURRENT_DATE - 6, NULL);

-- Lượt mượn còn hạn: Bình mượn Effective Java (phí = 0)
INSERT INTO loans (book_id, member_id, borrowed_at, due_date, returned_at)
VALUES (2, 2, CURRENT_DATE - 3, CURRENT_DATE + 11, NULL);