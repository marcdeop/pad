#ifndef FORTUNETHREAD_H
#define FORTUNETHREAD_H

#include <QThread>
#include <QTcpSocket>

class ChatQtServerThread : public QThread
{
    Q_OBJECT

public:
    ChatQtServerThread(int socketDescriptor, const QString &message, QObject *parent);

    void run() Q_DECL_OVERRIDE;

signals:
    void error(QTcpSocket::SocketError socketError);

private:
    int socketDescriptor;
    QString text;
};

#endif

