package com.lambda.bank;

import com.lambda.bank.model.Pago;
import com.lambda.bank.persistencia.PagoPersistencia;
import java.util.Date;
import java.util.List;

/**
 * Hello world!
 *
 */
public class BankApp {
    /**
     * Funcion que recibe POST de pago
     * @param pago pago a registrar
     */
    public static void pago(Pago pago){
        PagoPersistencia data = new PagoPersistencia();
        pago.setFechaPago(new Date(System.currentTimeMillis()));
        data.save(pago);
    }

    /**
     * Funcion get de pagos
     * @return Lista de todos los pagos registrados
     */
    public static List<Pago> pagos(){
        PagoPersistencia data = new PagoPersistencia();
        return data.pagos();
    }

}
