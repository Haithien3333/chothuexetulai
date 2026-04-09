-- Cập nhật booking gần nhất thành COMPLETED để test
UPDATE bookings SET status = 'COMPLETED' WHERE id = (SELECT MAX(id) FROM (SELECT id FROM bookings) AS t);
