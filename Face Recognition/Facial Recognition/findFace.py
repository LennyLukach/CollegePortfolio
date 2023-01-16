import numpy as np
import cv2
import pickle

face_cascade = cv2.CascadeClassifier("Facial Recognition/cascades/data/haarcascade_frontalface_alt2.xml")
eye_cascade = cv2.CascadeClassifier("Facial Recognition/cascades/data/haarcascade_eye.xml")
recognizer = cv2.face.LBPHFaceRecognizer_create()
recognizer.read("trainer.yml")

labels = {}
with open("labels.pickle", "rb") as f:
    first_labels = pickle.load(f)
    labels = {v : k for k, v in first_labels.items()}

cap = cv2.VideoCapture(0)

while True:
    rect, img = cap.read()
    img = cv2.flip(img, 1)

    grey = cv2.cvtColor(img, cv2.COLOR_BGR2GRAY)
    faces = face_cascade.detectMultiScale(grey, scaleFactor=1.5, minNeighbors=5)



    for (x, y, w, h) in faces:
        #print (x, y, w, h)
        roi_grey = grey[y : y + h, x : x + w]
        roi_color = img[y : y + h, x : x + w]

        id_, conf = recognizer.predict(roi_grey)
        if conf >= 40: #nd conf <= 85:
            #print(id_)
            #print(labels[id_])
            font = cv2.FONT_HERSHEY_SIMPLEX
            name = labels[id_]
            color = (255, 0, 0) # works with BGR
            stroke = 2
            cv2.putText(img, "Guess: " + name, (20, 50), font, 1.5, color, stroke, cv2.LINE_AA)
            cv2.putText(img, "Confidence: " + str(round(conf, 2)) + "%", (20, 110), font, 1.5, color, stroke, cv2.LINE_AA)

        img_item = "my-image.png"
        cv2.imwrite(img_item, roi_grey)

        color = (0, 255, 0) #? Works in BGR
        stroke = 2
 
        ec_x = x + w
        ec_y = y + h
        cv2.rectangle(img, (x, y), (ec_x, ec_y), color, stroke)
        eyes = eye_cascade.detectMultiScale(roi_grey)
        for (ex, ey, ew, eh) in eyes:
            cv2.rectangle(roi_color, (ex, ey), (ex+ew, ey+eh), (90, 17, 16), 2)

    cv2.imshow("Facial Recognition", img)

    if cv2.waitKey(1) & 0xFF == ord("q"):
        break

cap.release()
cv2.destroyAllWindows()