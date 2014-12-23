/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils;

/**
 *
 * @author Nakim
 */
public enum ReturnValue
{
    SUCCESS(0),
    FAILURE(-1);

    private final int returnCode;

    private ReturnValue(int returnCode)
    {
        this.returnCode = returnCode;
    }

    public int getReturnCode()
    {
        return returnCode;
    }
}
