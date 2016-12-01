#include "mainwindow.h"
#include "ui_mainwindow.h"
#include <QInputDialog>

MainWindow::MainWindow(QTcpSocket * socket, QWidget *parent) :
    QMainWindow(parent),
    ui(new Ui::MainWindow)
{
    this->socket = socket;

    ui->setupUi(this);

    addActions();
    addMenus();
    connectSlots();
    statusBar()->showMessage("Disconnected");

}

void MainWindow::connectSlots(){
    // on click or pressing enter we send our message
    connect(ui->pushButton,&QPushButton::clicked, this, &MainWindow::sendMessage);
    connect(ui->lineEdit,&QLineEdit::returnPressed,this,&MainWindow::sendMessage);


    // whenever there is something to read from the socket we call receiveText
    connect(socket, SIGNAL(readyRead()),this, SLOT(receiveText()));

    // whenever we connect/disconnect we need to perform some actions
    connect(socket, SIGNAL(connected()), this, SLOT(onConnected()));
    connect(socket, SIGNAL(disconnected()), this, SLOT(onDisconnected()));

    // connect local signal and slots
    connect(this,&MainWindow::addNick,this, &MainWindow::onAddNick);
    connect(this,&MainWindow::removeNick,this, &MainWindow::onRemoveNick);

    connect(this,&MainWindow::connectTo,this, &MainWindow::onConnectTo);
    connect(this,&MainWindow::disconnect,this, &MainWindow::onDisconnect);

    // connect actions to slots
    connect(exitAction, &QAction::triggered, this, &MainWindow::close);
    connect(changeNickAction, &QAction::triggered, this, &MainWindow::changeNick);
    connect(connectionAction, &QAction::triggered, this, &MainWindow::toggleConnection);
}

void MainWindow::onConnectTo(QString host, int port){
   // Connect to the server
   socket->connectToHost(host, port);

   // Wait until we stablish a connection
   if(!socket->waitForConnected(5000))
   {
       qDebug() << "Error: " << socket->errorString();
   }
}

void MainWindow::onDisconnect(){
    ui->listWidget->clear();
    socket->close();
}

void MainWindow::onConnected(){
    connectionAction->setText("Disconnect from server");
    // ToDo: for got's sake,  fix this port thing!!!
    statusBar()->showMessage("Connected to "+socket->peerName()+" on port "+socket->peerPort());
    changeNickAction->setEnabled(true);
}

void MainWindow::onDisconnected(){
    connectionAction->setText("Connect to server");
    changeNickAction->setEnabled(false);
    statusBar()->showMessage("Disconnected");
}

void MainWindow::changeNick(){
        bool ok;
        QString nick = QInputDialog::getText(this, tr("Change nick"),
                                             tr("Desired Nick:"), QLineEdit::Normal,
                                             "Marc", &ok);
        if (ok){
            //socket->write(QByteArray(ui->lineEdit->text().toUtf8().append("\n")));
            socket->write(QByteArray("/nick "+nick.toUtf8()+"\n"));
            socket->flush();
        }
}

void MainWindow::toggleConnection(){
    if(socket->isOpen()){
        emit disconnect();
    }
    else {
        bool portOK;
        int port = QInputDialog::getInt(this, tr("Server Port"),
                                     tr("Port:"), 2000, 0, 65535, 1, &portOK);
        bool hostOK;
        QString host = QInputDialog::getText(this, tr("Server Host"),
                                             tr("Host:"), QLineEdit::Normal,
                                             "localhost", &hostOK);
        if (portOK && hostOK)
            emit connectTo(host,port);
    }
}

void MainWindow::addActions(){
    exitAction = new QAction(tr("&Close"), this);

    connectionAction = new QAction(tr("&Connection"), this);
    connectionAction->setText(tr("Connect to server"));

    changeNickAction = new QAction(tr("ChangeNick"), this);
    changeNickAction->setText(tr("Change nick to"));
    changeNickAction->setEnabled(false);
}

void MainWindow::addMenus(){
    fileMenu = menuBar()->addMenu(tr("&File"));

    fileMenu->addAction(connectionAction);
    fileMenu->addAction(changeNickAction);
    fileMenu->addAction(exitAction);
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
