package com.lambda.bank.persistencia;

import com.lambda.bank.model.Pago;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.WriteConcern;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.types.ObjectId;
import java.util.ArrayList;
import java.util.List;

public class PagoPersistencia {

    private MongoDatabase database;

    private MongoClient mongoClient;

    /**
     * Crea conexion a la base de datos
     */
    public PagoPersistencia(){
        ConnectionString connString = new ConnectionString(
                "mongodb+srv://test:test@cluster0.2xxny.mongodb.net"
        );
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(connString)
                .retryWrites(true)
                .build();
        mongoClient = MongoClients.create(settings);
        database = mongoClient.getDatabase("Banco");
    }

    /**
     *
     * @param pago pago a almacenar
     */
    public void save(Pago pago) {
        Document doc = new Document("_id",new ObjectId());
        doc.append("fecha",pago.getFechaPago());
        doc.append("valor",pago.getMontoPago());
        doc.append("factura",pago.getNumeroFactura());
        MongoCollection<Document> collection = database.getCollection("Pagos").withWriteConcern(WriteConcern.MAJORITY);
        collection.insertOne(doc);
        //System.out.println(collection.count());
    }

    /**
     *
     * @return todos los pagos registrados
     */
    public List<Pago> pagos() {
        ArrayList<Pago> pagos = new ArrayList<Pago>();
        for(Document d: database.getCollection("Pagos").find()){
            Pago p = new Pago();
            p.setNumeroFactura(d.getString("factura"));
            p.setMontoPago(d.getInteger("valor"));
            p.setFechaPago(d.getDate("fecha"));
            pagos.add(p);
        }
        return pagos;
    }
}
