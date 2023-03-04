CREATE TABLE "book"
(
    "id"         INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    "title"      VARCHAR     NOT NULL,
    "author"     VARCHAR     NOT NULL,
    "price"      BIGINT      NOT NULL,
    "created_at" TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    "updated_at" TIMESTAMPTZ NOT NULL DEFAULT NOW()
);
