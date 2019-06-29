package com.example.rpw003.myapplication;

public class ReadingEntry
    {
        private String date;
        private int pagesRead;

        public ReadingEntry(String date, int pagesRead)
            {
                this.date = date;
                this.pagesRead = pagesRead;

            } // ReadingEntry

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public int getPagesRead() {
            return pagesRead;
        }

        public void setPagesRead(int pagesRead) {
            this.pagesRead = pagesRead;
        }

        @Override
        public String toString()
            {
                return "ReadingEntry{" +
                    "date='" + date + '\'' +
                    ", pagesRead=" + pagesRead +
                    '}';

            } // toString

    } // class ReadingEntry
