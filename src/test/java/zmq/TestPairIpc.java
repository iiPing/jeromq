/*
    Copyright (c) 2007-2014 Contributors as noted in the AUTHORS file

    This file is part of 0MQ.

    0MQ is free software; you can redistribute it and/or modify it under
    the terms of the GNU Lesser General Public License as published by
    the Free Software Foundation; either version 3 of the License, or
    (at your option) any later version.

    0MQ is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Lesser General Public License for more details.

    You should have received a copy of the GNU Lesser General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/

package zmq;

import org.junit.Test;
import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;

public class TestPairIpc
{
    //  Create REQ/ROUTER wiring.

    @Test
    public void testPairIpc()
    {
        Ctx ctx = ZMQ.zmqInit(1);
        assertThat(ctx, notNullValue());
        SocketBase sb = ZMQ.zmq_socket(ctx, ZMQ.ZMQ_PAIR);
        assertThat(sb, notNullValue());
        boolean brc = ZMQ.zmq_bind(sb, "ipc:///tmp/tester");
        assertThat(brc, is(true));

        SocketBase sc = ZMQ.zmq_socket(ctx, ZMQ.ZMQ_PAIR);
        assertThat(sc, notNullValue());
        brc = ZMQ.zmq_connect(sc, "ipc:///tmp/tester");
        assertThat(brc, is(true));

        Helper.bounce(sb, sc);

        //  Tear down the wiring.
        ZMQ.zmq_close(sb);
        ZMQ.zmq_close(sc);
        ZMQ.zmq_term(ctx);
    }
}
