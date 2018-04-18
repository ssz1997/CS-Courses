import sys
from PyQt5.QtGui import QPainter
from PyQt5.QtWidgets import QWidget, QApplication
from PyQt5.QtCore import Qt
from PyQt5.QtGui import QPen
from PyQt5.QtCore import QRectF
from PyQt5.QtGui import QColor
from PyQt5.QtGui import QBrush

class Olympic(QWidget):
    
  def __init__(self):
    super().__init__()           
    self.setGeometry(300, 300, 550, 600)
    self.setWindowTitle('Olympic Rings')
    self.show()              
    self.x = 0
    self.updatesEnabled()
    
  def mousePressEvent(self,event):
      if ((event.x()-95) ** 2 + (event.y() - 100) ** 2) ** (1/2) <= 80 and ((event.x()-185) ** 2 + (event.y() - 200) ** 2) ** (1/2) <= 80:
        self.x = 1
        self.update()
        
      elif ((event.x()-275) ** 2 + (event.y() - 100) ** 2) ** (1/2) <= 80 and ((event.x()-185) ** 2 + (event.y() - 200) ** 2) ** (1/2) <= 80:
        self.x = 2
        self.update()
        
      elif ((event.x()-275) ** 2 + (event.y() - 100) ** 2) ** (1/2) <= 80 and ((event.x()-365) ** 2 + (event.y() - 200) ** 2) ** (1/2) <= 80:
        self.x = 3
        self.update()
        
      elif ((event.x()-455) ** 2 + (event.y() - 100) ** 2) ** (1/2) <= 80 and ((event.x()-365) ** 2 + (event.y() - 200) ** 2) ** (1/2) <= 80:
        self.x = 4
        self.update()
        
      elif ((event.x()-95) ** 2 + (event.y() - 100) ** 2) ** (1/2) <= 80:
        self.x = 5
        self.update()

      elif ((event.x()-275) ** 2 + (event.y() - 100) ** 2) ** (1/2) <= 80:
        self.x = 6
        self.update()

      elif ((event.x()-455) ** 2 + (event.y() - 100) ** 2) ** (1/2) <= 80:
        self.x = 7
        self.update()

      elif ((event.x()-185) ** 2 + (event.y() - 200) ** 2) ** (1/2) <= 80:
        self.x = 8
        self.update()

      elif ((event.x()-365) ** 2 + (event.y() - 200) ** 2) ** (1/2) <= 80:
        self.x = 9
        self.update()
      else:
        self.x = 10
        self.update()
    


    
  def paintEvent(self, event):
    qp = QPainter()
    
    qp.begin(self)
 
    qp.setPen(QPen(QColor(0,107,176), 8, Qt.SolidLine))
    qp.drawEllipse(15,20,160,160)
    qp.setPen(QPen(QColor(29,24,21), 8, Qt.SolidLine))
    qp.drawEllipse(195,20,160,160)
    qp.setPen(QPen(QColor(220,47,31), 8, Qt.SolidLine))
    qp.drawEllipse(375,20,160,160)
    qp.setPen(QPen(QColor(239,169,13), 8, Qt.SolidLine))
    qp.drawEllipse(105,120,160,160)
    qp.setPen(QPen(QColor(5,147,65), 8, Qt.SolidLine))   
    qp.drawEllipse(285,120,160,160)
    qp.setPen(QPen(QColor(0,107,176), 8, Qt.SolidLine))
    qp.drawArc(15,20,160,160,0,-720)
    qp.setPen(QPen(QColor(29,24,21), 8, Qt.SolidLine))
    qp.drawArc(195,20,160,160,3600,4320)
    qp.setPen(QPen(QColor(5,147,65), 8, Qt.SolidLine))
    qp.drawArc(285,120,160,160,2160,2880)
    qp.setPen(QPen(QColor(220,47,31), 8, Qt.SolidLine))
    qp.drawArc(375,20,160,160,3600,4320)
    

    
    if self.x == 1:
      qp.setBrush(QBrush(QColor(0,107,176), Qt.SolidPattern))
      qp.setPen(QPen(QColor(0,107,176), Qt.SolidLine))
      qp.drawRect(75,400,200,100)
      qp.setBrush(QBrush(QColor(239,169,13), Qt.SolidPattern))
      qp.setPen(QPen(QColor(239,169,13), Qt.SolidLine))
      qp.drawRect(275,400,200,100)

    if self.x == 2:
      qp.setBrush(QBrush(QColor(29,24,21), Qt.SolidPattern))
      qp.setPen(QPen(QColor(29,24,21), Qt.SolidLine))
      qp.drawRect(75,400,200,100)
      qp.setBrush(QBrush(QColor(239,169,13), Qt.SolidPattern))
      qp.setPen(QPen(QColor(239,169,13), Qt.SolidLine))
      qp.drawRect(275,400,200,100)

    if self.x == 3:
      qp.setBrush(QBrush(QColor(29,24,21), Qt.SolidPattern))
      qp.setPen(QPen(QColor(29,24,21), Qt.SolidLine))
      qp.drawRect(75,400,200,100)
      qp.setBrush(QBrush(QColor(5,147,65), Qt.SolidPattern))
      qp.setPen(QPen(QColor(5,147,65), Qt.SolidLine))
      qp.drawRect(275,400,200,100)

    if self.x == 4:
      qp.setBrush(QBrush(QColor(220,47,31), Qt.SolidPattern))
      qp.setPen(QPen(QColor(220,47,31), Qt.SolidLine))
      qp.drawRect(75,400,200,100)
      qp.setBrush(QBrush(QColor(5,147,65), Qt.SolidPattern))
      qp.setPen(QPen(QColor(5,147,65), Qt.SolidLine))
      qp.drawRect(275,400,200,100)

    if self.x == 5:
      qp.setBrush(QBrush(QColor(0,107,176), Qt.SolidPattern))
      qp.setPen(QPen(QColor(0,107,176), Qt.SolidLine))
      qp.drawRect(75,400,400,100)
     
    if self.x ==6:
      qp.setBrush(QBrush(QColor(29,24,21), Qt.SolidPattern))
      qp.setPen(QPen(QColor(29,24,21), Qt.SolidLine))
      qp.drawRect(75,400,400,100)

    if self.x ==7:
      qp.setBrush(QBrush(QColor(220,47,31), Qt.SolidPattern))
      qp.setPen(QPen(QColor(220,47,31), Qt.SolidLine))
      qp.drawRect(75,400,400,100)

    if self.x ==8:
      qp.setBrush(QBrush(QColor(239,169,13), Qt.SolidPattern))
      qp.setPen(QPen(QColor(239,169,13), Qt.SolidLine))
      qp.drawRect(75,400,400,100)

    if self.x ==9:
      qp.setBrush(QBrush(QColor(5,147,65), Qt.SolidPattern))
      qp.setPen(QPen(QColor(5,147,65), Qt.SolidLine))
      qp.drawRect(75,400,400,100)

    if self.x == 10:
      qp.setBrush(QBrush(QColor(5,147,65), Qt.SolidPattern))

    qp.end
    
  
    

    
if __name__ == '__main__':  
  app = QApplication(sys.argv)
  ex = Olympic()
  sys.exit(app.exec_())
  
