#ifndef FORTUNESERVER_H
#define FORTUNESERVER_H

#include <QStringList>
#include <QTcpServer>

class ChatQtServer : public QTcpServer
{
    Q_OBJECT

public:
    ChatQtServer(QObject *parent = 0);

protected:
    void incomingConnection(qintptr socketDescriptor) Q_DECL_OVERRIDE;

private:
};

#endif

