ALTER TABLE cm.modification_model ADD COLUMN deleted BOOLEAN DEFAULT false NOT NULL;
ALTER TABLE cm.modification_model ADD COLUMN removed_at TIMESTAMP WITHOUT TIME ZONE;
ALTER TABLE cm.modification_model ADD COLUMN changed_at TIMESTAMP WITHOUT TIME ZONE;
