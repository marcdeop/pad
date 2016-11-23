#include "mainwindow.h"
#include "ui_mainwindow.h"

MainWindow::MainWindow(QWidget *parent) :
    QMainWindow(parent),
    ui(new Ui::MainWindow)
{
    ui->setupUi(this);
    socket = new QTcpSocket(this);

    // this is not blocking call
    socket->connectToHost("localhost", 2000);

    // we need to wait...
    if(!socket->waitForConnected(5000))
    {
        qDebug() << "Error: " << socket->errorString();
    }

    // on click or pressing enter we send our message
    connect(ui->pushButton,&QPushButton::clicked, this, &MainWindow::sendMessage);
    connect(ui->lineEdit,&QLineEdit::returnPressed,this,&MainWindow::sendMessage);


    // whenever there is something to read from the socket we call receiveText
    connect(socket, SIGNAL(readyRead()),this, SLOT(receiveText()));

    // connect local signal and slots
    connect(this,&MainWindow::addNick,this, &MainWindow::onAddNick);
    connect(this,&MainWindow::removeNick,this, &MainWindow::onRemoveNick);
}

MainWindow::~MainWindow()
{
    delete ui;
    socket->close();
}


void MainWindow::sendMessage(){
    socket->write(QByteArray(ui->lineEdit->text().toUtf8().append("\n")));
    ui->lineEdit->clear();
    socket->flush();
}

void MainWindow::receiveText(){
    while(!socket->atEnd()){
        // removing the \n is ugly but needed with the current protocol that we use
        QByteArray line = socket->readLine();
        if (line.endsWith("\n")){
            line.chop(sizeof(char));
        }

        if(line.startsWith("/add ")){
            emit addNick(line.remove(0,sizeof("/add")));
        } else
        if(line.startsWith("/remove ")){
            emit removeNick(line.remove(0,sizeof("/remove")));
        } else
        if(line.startsWith("/update ")){
            line=line.remove(0,sizeof("/update"));
            QList<QByteArray> nicks = line.split(' ');
            emit removeNick(nicks[0]);
            emit addNick(nicks[1]);
        } else {
          ui->textBrowser->append(line);
        }
    }
}


void MainWindow::onAddNick(QByteArray nick){
    ui->listWidget->addItem(nick);
}

void MainWindow::onRemoveNick(QByteArray nick){
    ui->listWidget->takeItem(ui->listWidget->row(ui->listWidget->findItems(QString(nick),0).first()));
}
